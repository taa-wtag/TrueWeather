package com.rektstudios.trueweather.presentation

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    lateinit var currentCity: CityItem
    private lateinit var currentWeather: Flow<WeatherHourItem?>
    private lateinit var currentCityForecastWeather: Pair<Flow<WeatherDayItem>?, Flow<WeatherHourItem>?>
    lateinit var isMetric : MutableLiveData<Boolean>
        private set
    lateinit var isCelsius : MutableLiveData<Boolean>
        private set


    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityList = getCityListUseCase.invoke()
            currentCity = currentCityUseCase.getCurrentCity()
            getWeatherData()
            isMetric.postValue( userPrefsUseCase.getIsMetric())
            isCelsius.postValue(userPrefsUseCase.getIsCelsius())
        }
    }

    fun setCurrentCity(cityItem: CityItem){
        currentCity = cityItem
        viewModelScope.launch(Dispatchers.IO) {
            currentCityUseCase.setCurrentCity(cityItem)
            getWeatherData()
        }
    }
    fun setCurrentCityFromGPS() = viewModelScope.launch(Dispatchers.IO) {
        currentCity = currentCityUseCase.getCurrentCity()
    }

    private suspend fun getWeatherData(){
        currentWeather = getCurrentWeatherUseCase.invoke(currentCity)
        currentCityForecastWeather = getForecastWeatherUseCase.invoke(currentCity)
    }

    fun toggleMetric(){
        viewModelScope.launch(Dispatchers.IO) {
            userPrefsUseCase.toggleMetric()
            isMetric.postValue(userPrefsUseCase.getIsMetric())
        }
    }
    fun toggleCelsius(){
        viewModelScope.launch(Dispatchers.IO) {
            userPrefsUseCase.toggleCelsius()
            isCelsius.postValue(userPrefsUseCase.getIsCelsius())
        }
    }

}