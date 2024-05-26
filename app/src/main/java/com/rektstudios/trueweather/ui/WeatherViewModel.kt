package com.rektstudios.trueweather.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.reponses.mapbox.SearchResponse
import com.rektstudios.trueweather.other.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.other.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.other.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.other.Constants.KEY_COUNTRY_NAME
import com.rektstudios.trueweather.other.Constants.KEY_METRIC
import com.rektstudios.trueweather.other.Event
import com.rektstudios.trueweather.other.Resource
import com.rektstudios.trueweather.repositories.ICityRepository
import com.rektstudios.trueweather.repositories.IPrefsRepository
import com.rektstudios.trueweather.repositories.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val cityRepository: ICityRepository,
    private val weatherRepository: IWeatherRepository,
    private val prefsRepository: IPrefsRepository
): ViewModel() {
    private lateinit var cityList: Flow<CityItem>
    private lateinit var currentCity: CityItem
    private lateinit var currentWeather: Flow<WeatherHourItem?>
    private lateinit var currentCityHourlyWeather: Flow<WeatherHourItem?>
    private lateinit var currentCityDailyWeather: Flow<WeatherDayItem?>
    var isMetric = true
    var isCelsius = true

    private val _cities = MutableLiveData<Event<Resource<SearchResponse>>>()
    val images: LiveData<Event<Resource<SearchResponse>>> = _cities

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityList = cityRepository.observeCityList()
            isMetric = isPreferenceMetric()
            isCelsius = isPreferenceCelsius()
        }
    }

    fun setCurrentCity(location: Location? = null){
        var cityName = ""
        var countryName = ""
        viewModelScope.launch(Dispatchers.IO) {
            if (location != null) {
                val response = weatherRepository.getCityName(location)
                response.data?.first().let {
                    if (it != null) {
                        cityName=it.name
                        countryName=it.country
                    }
                }
            }
            if(location==null || cityName.isEmpty() || countryName.isEmpty()){
                cityName=prefsRepository.readValue(KEY_CITY_NAME)
                countryName=prefsRepository.readValue(KEY_COUNTRY_NAME)
            }
            var city = cityList.first { cityItem -> cityItem.cityName == cityName && cityItem.country == countryName}
            if (!city.isValid)
                cityRepository.addCity(cityName,countryName)
            city = cityList.first { cityItem -> cityItem.cityName == cityName && cityItem.country == countryName}
            if (!city.isValid)
                city=cityList.first()
            setCurrentCity(city)
        }
    }

    fun setCurrentCity(cityItem: CityItem){
        currentCity = cityItem
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.saveValue(KEY_CITY_NAME,cityItem.cityName)
            prefsRepository.saveValue(KEY_COUNTRY_NAME,cityItem.country)
            currentWeather = weatherRepository.getCurrentWeather(cityItem)
            currentCityHourlyWeather = weatherRepository.getWeatherForecastInHours(cityItem, FORECAST_MAX_DAYS)
            currentCityDailyWeather = weatherRepository.getWeatherForecastInDays(cityItem, FORECAST_MAX_DAYS)
        }
    }

    private suspend fun isPreferenceMetric(): Boolean{
        return prefsRepository.readValue(KEY_METRIC).toBoolean()
    }
    private suspend fun isPreferenceCelsius(): Boolean{
        return prefsRepository.readValue(KEY_CELSIUS).toBoolean()
    }

    fun toggleMetric(){
        viewModelScope.launch(Dispatchers.IO) {
            isMetric=!isMetric
            prefsRepository.saveValue(KEY_METRIC,isMetric.toString())
        }
    }
    fun toggleCelsius(){
        viewModelScope.launch(Dispatchers.IO) {
            isCelsius=!isCelsius
            prefsRepository.saveValue(KEY_CELSIUS,isCelsius.toString())
        }
    }

    fun searchCities(searchQuery: String){
        if(searchQuery.isEmpty()) return
        _cities.value= Event(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            val response = cityRepository.searchForPlaces(searchQuery)
            _cities.value = Event(response)
        }
    }
}