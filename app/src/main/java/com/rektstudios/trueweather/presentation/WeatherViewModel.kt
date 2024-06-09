package com.rektstudios.trueweather.presentation

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
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
): ViewModel() {
    var currentCity: CityItem? = null
        private set
    val isMetric = MutableStateFlow<Boolean?>(null)
    val isCelsius = MutableStateFlow<Boolean?>(null)
    val cityList = MutableStateFlow<List<CityItem>>(emptyList())
    val currentWeather = MutableStateFlow<WeatherHourItem?>(null)
    val currentCityForecastWeatherDay = MutableStateFlow<List<WeatherDayItem>>(emptyList())
    val currentCityForecastWeatherHour= MutableStateFlow<List<WeatherHourItem>>(emptyList())


    init {
        viewModelScope.launch {
            launch { userPrefsUseCase.getIsCelsius().collect(isCelsius)}
            launch { userPrefsUseCase.getIsMetric().collect(isMetric)}
            launch { getCityListUseCase().collect{cityList.value=it}}
            launch { currentCityUseCase.getCurrentCity()?.let { setCurrentCityAndWeather(it) } }
        }
    }

    fun setCurrentCityAndWeather(cityItem: CityItem){
        viewModelScope.launch {
            if(checkCityInCityList(cityItem)) {
                launch {  currentCity = cityItem}
                launch {  currentCityUseCase.setCurrentCity(cityItem.cityName)}
                launch {  getCurrentWeatherUseCase(cityItem.cityName).collect(currentWeather)}
                launch {  getForecastWeatherUseCase.getWeatherDay(cityItem.cityName).collect(currentCityForecastWeatherDay)}
                launch {  getForecastWeatherUseCase.getWeatherHour(cityItem.cityName).collect(currentCityForecastWeatherHour)}
            }
        }
    }
    fun setCurrentCityFromGPS() = viewModelScope.launch {
        currentCityUseCase.getCurrentCity(true)?.let { setCurrentCityAndWeather(it) }
    }


    fun toggleMetric(){
        viewModelScope.launch  {
            isMetric.firstOrNull()?.let { userPrefsUseCase.setMetric(!it) }
        }
    }
    fun toggleCelsius(){
        viewModelScope.launch  {
            isCelsius.firstOrNull()?.let {  userPrefsUseCase.setCelsius(!it)}
        }
    }

    private suspend fun checkCityInCityList(cityItem: CityItem): Boolean{
        return cityList.firstOrNull()?.find { it.cityName == cityItem.cityName } != null
    }

}