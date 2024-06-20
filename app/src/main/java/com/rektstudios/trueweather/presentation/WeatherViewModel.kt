package com.rektstudios.trueweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val userPrefsUseCase: UserPrefsUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val currentCityUseCase: CurrentCityUseCase
) : ViewModel() {
    var currentCity: String = ""
        private set
    val isMetric = MutableStateFlow<Boolean?>(null)
    val isCelsius = MutableStateFlow<Boolean?>(null)
    val cityList = MutableStateFlow<List<CityItem>>(emptyList())
    val currentCityDailyWeatherForecast = MutableStateFlow<List<DailyWeatherItem>>(emptyList())
    val currentCityHourlyWeatherForecast = MutableStateFlow<List<HourlyWeatherItem>>(emptyList())


    init {
        viewModelScope.launch {
            launch { userPrefsUseCase.getIsCelsius().collect(isCelsius) }
            launch { userPrefsUseCase.getIsMetric().collect(isMetric) }
            launch { getCityListUseCase().collect { cityList.value = it } }
            launch {
                currentCityUseCase.getCurrentCity()
                    ?.let { it.cityName?.let { it1 -> setCurrentCityAndWeather(it1) } }
            }
        }
    }

    fun setCurrentCityAndWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) {
                currentCity = city
                currentCityDailyWeatherForecast.emit(emptyList())
                currentCityHourlyWeatherForecast.emit(emptyList())
            } else if (checkCityInCityList(city)) {
                currentCity = city
                launch { currentCityUseCase.setCurrentCity(city) }
                launch { getCurrentWeatherUseCase(city) }
                launch {
                    getForecastWeatherUseCase.getWeatherDay(city)
                        .collect(currentCityDailyWeatherForecast)
                }
                launch {
                    getForecastWeatherUseCase.getWeatherHour(city)
                        .collect(currentCityHourlyWeatherForecast)
                }
            }
        }
    }

    fun setCurrentCityFromGPS() = viewModelScope.launch {
        val cityCount = cityList.value.size
        currentCityUseCase.getCurrentCityFromLocation()?.let {
            if (cityList.value.size != cityCount) it.cityName?.let { it1 ->
                setCurrentCityAndWeather(it1)
            }
        }
    }


    fun toggleMetric() {
        viewModelScope.launch {
            isMetric.firstOrNull()?.let { userPrefsUseCase.setMetric(!it) }
        }
    }

    fun toggleCelsius() {
        viewModelScope.launch {
            isCelsius.firstOrNull()?.let { userPrefsUseCase.setCelsius(!it) }
        }
    }

    private suspend fun checkCityInCityList(city: String): Boolean {
        return cityList.firstOrNull()?.find { it.cityName == city } != null
    }

}