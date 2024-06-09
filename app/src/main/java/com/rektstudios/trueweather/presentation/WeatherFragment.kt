package com.rektstudios.trueweather.presentation

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.rektstudios.trueweather.databinding.FragmentWeatherBinding
import com.rektstudios.trueweather.presentation.adapters.CityCardAdapter
import com.rektstudios.trueweather.presentation.adapters.WeatherDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    @Inject
    lateinit var weatherDayAdapter: WeatherDayAdapter

    @Inject
    lateinit var cityCardAdapter: CityCardAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if(it[Manifest.permission.ACCESS_FINE_LOCATION] == true && it[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
                isLocationPermissionGranted = true
        }
        permissionLauncher.launch(locationPermissions)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewPager()
        subscribeToObservers()
        binding.floatingActionButtonGetLocation.setOnClickListener {
            if(isLocationPermissionGranted)
                viewModel.setCurrentCityFromGPS()
            else
                permissionLauncher.launch(locationPermissions)
        }
    }

    private fun subscribeToObservers() {
        cityCardAdapter.navigateToCityFragment = { findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCitiesFragment())}
        weatherDayAdapter.registerAdapterDataObserver(
            object: AdapterDataObserver(){
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart,itemCount)
                    binding.recyclerViewWeatherDayItem.smoothScrollToPosition(0)
                }
            }
        )
        cityCardAdapter.registerAdapterDataObserver(
            object: AdapterDataObserver(){
                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart,itemCount)
                    binding.viewPagerCityCard.setCurrentItem(0,true)
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    binding.viewPagerCityCard.setCurrentItem(positionStart,true)
                }
            }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            launch{viewModel.currentCityForecastWeatherDay.collect{ weatherDayAdapter.weatherDayItems = it.sortedBy { item -> item.dateEpoch } }}
            launch{viewModel.currentCityForecastWeatherHour.collect {weatherDayAdapter.weatherHourAdapter.weatherHourItems = it
                .filter { item-> item.time.substring(11,13).toInt()>it.first().time.substring(11,13).toInt()}}}
            launch {
                viewModel.cityList.collect {
                    if (it.isNotEmpty()) { cityCardAdapter.cityItems = it.map { cityItem ->Pair(cityItem, cityItem.weatherEveryHour.firstOrNull()) } }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewWeatherDayItem.apply {
            adapter = weatherDayAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupViewPager() {
        binding.viewPagerCityCard.apply {
            adapter = cityCardAdapter
            registerOnPageChangeCallback(pageChangedCallback)
        }
        binding.apply {
            TabLayoutMediator(tabLayoutViewPagerDots,viewPagerCityCard){ _, _ -> }.attach()
        }
    }

    private val pageChangedCallback = object: OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if(position!=cityCardAdapter.cityItems.size)
                viewModel.setCurrentCityAndWeather(cityCardAdapter.cityItems[position].first)
        }
    }
}