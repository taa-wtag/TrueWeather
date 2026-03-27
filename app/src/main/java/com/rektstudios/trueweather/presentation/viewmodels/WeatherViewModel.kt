package com.rektstudios.trueweather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.toCityCardData
import com.rektstudios.trueweather.data.local.entity.toDailyWeatherData
import com.rektstudios.trueweather.data.local.entity.toWeatherHourCardData
import com.rektstudios.trueweather.domain.data.CityCardData
import com.rektstudios.trueweather.domain.data.CityState
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WeatherViewModel
    @Inject
    constructor(
        private val userPrefsUseCase: UserPrefsUseCase,
        private val getCityListUseCase: GetCityListUseCase,
        private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
        private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
        private val currentCityUseCase: CurrentCityUseCase,
    ) : ViewModel() {
        private val currentCity = MutableStateFlow("")
        private val isMetric = MutableStateFlow(true)
        private val isCelsius = MutableStateFlow(true)
        private val cityList = MutableStateFlow<List<CityEntity>>(emptyList())
        private val _cityCardList = MutableStateFlow<List<CityCardData>>(emptyList())
        val cityCardList = _cityCardList.asStateFlow()
        val currentCityState =
            combine(
                currentCity,
                cityList,
                isCelsius,
            ) { city, list, celsius ->
                Triple(city, list, celsius)
            }.flatMapLatest { (city, list, celsius) ->
                if (city.isEmpty() || list.none { it.cityName == city }) {
                    return@flatMapLatest flowOf(CityState())
                }

                combine(
                    getForecastWeatherUseCase.getWeatherHour(city),
                    getForecastWeatherUseCase.getWeatherDay(city),
                ) { hourlyData, dailyData ->
                    val initialHour = hourlyData.firstOrNull()
                    val dailyList = dailyData.filter { it.cityName == city }
                    val hourlyList = hourlyData.filter { it.cityName == city && filterHourlyList(initialHour, it) }

                    CityState(
                        hourlyList.map { it.toWeatherHourCardData() },
                        dailyList.map { it.toDailyWeatherData(celsius) },
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CityState(),
            )

        init {
            viewModelScope.launch(Dispatchers.IO) {
                launch { userPrefsUseCase.getIsCelsius().collect(isCelsius) }
                launch { userPrefsUseCase.getIsMetric().collect(isMetric) }
                launch {
                    getCityListUseCase().collectLatest {
                        cityList.value = it
                        currentCity.emit(it.firstOrNull()?.cityName.orEmpty())
                    }
                }
                launch {
                    currentCity
                        .collectLatest {
                            currentCityUseCase.setCurrentCity(it)
                        }
                }
                launch {
                    combine(
                        currentCity,
                        cityList,
                        isCelsius,
                    ) { city, list, celcius ->
                        Triple(city, list, celcius)
                    }.flatMapLatest { (city, list, celsius) ->
                        if (city.isEmpty() || list.none { it.cityName == city }) {
                            return@flatMapLatest flowOf(
                                list.map { city ->
                                    _cityCardList.value.find { it.name == city.cityName }
                                        ?: city.toCityCardData(null, celsius)
                                },
                            )
                        }

                        getCurrentWeatherUseCase(city).map { weather ->
                            list.map { city ->
                                weather?.takeIf { it.cityName == city.cityName }
                                    ?: return@map _cityCardList.value.find { it.name == city.cityName }
                                        ?: city.toCityCardData(null, celsius)
                                city.toCityCardData(weather, celsius)
                            }
                        }
                    }.collectLatest {
                        _cityCardList.emit(it)
                    }
                }
            }
        }

        fun setCurrentCity(city: String) =
            viewModelScope.launch(Dispatchers.IO) {
                currentCity.emit(city)
            }

        fun setCurrentCityFromGPS() =
            viewModelScope.launch {
                currentCityUseCase.getCurrentCityFromLocation()?.let {
                    if (checkCityInCityList(it.cityName)) {
                        setCurrentCity(it.cityName)
                    }
                }
            }

        fun refreshWeatherData(
            city: String = currentCity.value,
            stopRefreshing: () -> Unit,
        ) = viewModelScope.launch {
            if (city.isNotEmpty()) {
                getForecastWeatherUseCase.getWeatherFromRemote(city)
            }
            stopRefreshing()
        }

        fun toggleMetric() =
            viewModelScope.launch {
                userPrefsUseCase.setMetric(isMetric.value)
            }

        fun toggleCelsius() =
            viewModelScope.launch {
                userPrefsUseCase.setCelsius(isCelsius.value)
            }

        private fun checkCityInCityList(city: String): Boolean = cityList.value.find { it.cityName == city } != null

        private fun filterHourlyList(
            currentHour: HourlyWeatherEntity?,
            hourToCompare: HourlyWeatherEntity,
        ): Boolean {
            val initialHour = currentHour?.timeString?.substringBefore(" ") ?: return false
            val comparingHour = hourToCompare.timeString?.substringBefore(" ") ?: return false
            return initialHour == comparingHour
        }
    }
