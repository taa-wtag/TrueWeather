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
import java.util.Calendar
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
        requestPermissions()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewPager()
        subscribeToObservers()
        binding.floatingActionButtonGetLocation.setOnClickListener {
            if (isLocationPermissionGranted) {
                viewModel.setCurrentCityFromGPS()
            } else
                permissionLauncher.launch(locationPermissions)
        }
    }

    private fun subscribeToObservers() {
        cityCardAdapter.navigateToCityFragment =
            { findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCitiesFragment()) }
        weatherDayAdapter.registerAdapterDataObserver(
            object : AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    binding.recyclerViewWeatherDayItem.smoothScrollToPosition(0)
                }
            }
        )
        cityCardAdapter.registerAdapterDataObserver(
            object : AdapterDataObserver() {

            }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.currentCityDailyWeatherForecast.collect {
                    weatherDayAdapter.dailyWeatherItems = if (it.isNotEmpty())
                        it.sortedBy { item -> item.dateEpoch }
                    else emptyList()
                }
            }
            launch {
                viewModel.currentCityHourlyWeatherForecast.collect {
                    weatherDayAdapter.weatherHourAdapter.hourlyWeatherItems =
                        if (it.isNotEmpty()) it
                            .filter { item ->
                                item.timeString?.substring(11, 13)?.toInt()?.let { it1 ->
                                    it.first().timeString?.substring(11, 13)?.toInt()
                                        ?.let { it2 -> it1 > it2 }
                                } == true || (item.timeString?.substring(11, 13) == "00" &&
                                        item.timeEpoch?.let { it1 -> it1 > Calendar.getInstance().timeInMillis / 1000L } == true)
                            }
                        else emptyList()
                }
            }
            launch {
                viewModel.cityList.collect {
                    cityCardAdapter.cityItems = if (it.isNotEmpty()) it.map { cityItem ->
                        Pair(
                            cityItem,
                            cityItem.weatherEveryHour.firstOrNull()
                        )
                    } else emptyList()

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
            TabLayoutMediator(tabLayoutViewPagerDots, viewPagerCityCard) { _, _ -> }.attach()
        }
    }

    private val pageChangedCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position != cityCardAdapter.cityItems.size)
                cityCardAdapter.cityItems[position].first.cityName?.let {
                    viewModel.setCurrentCityAndWeather(it)
                }
            else
                viewModel.setCurrentCityAndWeather("")
        }
    }

    private fun requestPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true && it[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
                isLocationPermissionGranted = true
        }
        permissionLauncher.launch(locationPermissions)
    }
}