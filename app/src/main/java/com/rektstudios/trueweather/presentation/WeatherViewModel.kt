package com.rektstudios.trueweather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val userPrefsUseCase: UserPrefsUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val currentCityUseCase: CurrentCityUseCase
): ViewModel() {
    lateinit var cityList: Flow<CityItem> private set
    lateinit var currentCity: CityItem private set
    lateinit var currentWeather: Flow<WeatherHourItem?> private set
    lateinit var currentCityForecastWeather: Pair<Flow<WeatherDayItem>?, Flow<WeatherHourItem>?> private set
    lateinit var isMetric: Flow<Boolean> private set
    lateinit var isCelsius: Flow<Boolean> private set


    init {
        viewModelScope.launch {
            cityList = getCityListUseCase.invoke()
            currentCityUseCase.getCurrentCity()?.let { setCurrentCityAndWeather(it) }
            isMetric = userPrefsUseCase.getIsMetric()
            isCelsius = userPrefsUseCase.getIsCelsius()
        }
    }

    fun setCurrentCityAndWeather(cityItem: CityItem){
        viewModelScope.launch {
            if(checkCityInCityList(cityItem)) {
                currentCity = cityItem
                currentCityUseCase.setCurrentCity(cityItem)
                getWeatherData()
            }
        }
    }
    fun setCurrentCityFromGPS() = viewModelScope.launch {
        currentCityUseCase.getCurrentCity()?.let { setCurrentCityAndWeather(it) }
    }

    private suspend fun getWeatherData(){
        currentWeather = getCurrentWeatherUseCase.invoke(currentCity)
        currentCityForecastWeather = getForecastWeatherUseCase.invoke(currentCity)
    }

    fun toggleMetric(){
        viewModelScope.launch {
            isMetric.firstOrNull()?.let { userPrefsUseCase.setMetric(!it) }
        }
    }
    fun toggleCelsius(){
        viewModelScope.launch {
            isCelsius.firstOrNull()?.let {  userPrefsUseCase.setCelsius(!it)}
        }
    }

    private suspend fun checkCityInCityList(cityItem: CityItem): Boolean{
        return cityList.firstOrNull { it.cityName == cityItem.cityName } != null
    }

}