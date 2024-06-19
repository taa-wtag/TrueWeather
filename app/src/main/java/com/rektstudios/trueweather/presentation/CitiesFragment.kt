package com.rektstudios.trueweather.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rektstudios.trueweather.databinding.FragmentCitiesBinding
import com.rektstudios.trueweather.domain.util.Constants.CITY_GRID_SPAN
import com.rektstudios.trueweather.domain.util.Constants.SEARCH_TIME_DELAY
import com.rektstudios.trueweather.presentation.adapters.CityItemAdapter
import com.rektstudios.trueweather.presentation.adapters.SearchCityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CitiesFragment : Fragment() {
    private lateinit var binding: FragmentCitiesBinding
    private val viewModel: CityViewModel by viewModels()

    @Inject
    lateinit var cityItemAdapter: CityItemAdapter

    @Inject
    lateinit var searchCityAdapter: SearchCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCityRecyclerView()
        subscribeToObservers()
        setupSearchRecyclerView()
        binding.buttonAddCity.setOnClickListener {
            if(!binding.cardViewSearchCityModal.isVisible)
                binding.cardViewSearchCityModal.visibility = VISIBLE
        }
        var job: Job? = null
        binding.editTextSearchCity.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchCities(editable.toString())
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(binding.cardViewSearchCityModal.isVisible)
                binding.cardViewSearchCityModal.visibility = GONE
            else findNavController().popBackStack()
        }

    }

    private fun setupCityRecyclerView() {
        binding.recyclerViewCityItem.apply {
            adapter = cityItemAdapter
            layoutManager = GridLayoutManager(requireContext(),CITY_GRID_SPAN)
        }
        cityItemAdapter.setOnDeleteButtonClickListener {
            viewModel.deleteCity(it)
        }
    }

    private fun setupSearchRecyclerView() {
        binding.recyclerViewCitySuggestions.apply {
            adapter = searchCityAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        searchCityAdapter.setOnItemClickListener {
            viewModel.addCity(it)
            searchCityAdapter.cityItems= emptyList()
            binding.editTextSearchCity.text?.clear()
            binding.cardViewSearchCityModal.visibility = GONE
        }
    }
    private fun subscribeToObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                combine(viewModel.cityList,viewModel.currentWeatherForEachCity){cities, weathers->
                    cities.mapIndexed { position, cityItem ->Pair(cityItem, if(weathers.isEmpty() || position>=weathers.size)null else weathers[position]) }
                }.collect{
                    cityItemAdapter.cityItems = it
                }
            }
            launch {
                viewModel.suggestedCities.observe(viewLifecycleOwner) {
                    searchCityAdapter.cityItems = it
                }
            }
        }
    }
}