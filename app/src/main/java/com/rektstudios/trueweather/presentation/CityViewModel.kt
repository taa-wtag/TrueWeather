package com.rektstudios.trueweather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityViewModel @Inject constructor(
    private val userPrefsUseCase: UserPrefsUseCase,
    private val addCityUseCase: AddCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val getCitySuggestionsUseCase: GetCitySuggestionsUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
): ViewModel() {
    lateinit var cities: Flow<CityItem> private set
    var currentWeathers = mutableListOf<Pair<CityItem, Flow<WeatherHourItem?>>>()
        private set
    var suggestedCities =  MutableLiveData<List<CityItem>>(emptyList())
        private set
    lateinit var isMetric: Flow<Boolean> private set
    lateinit var isCelsius: Flow<Boolean> private set

    init {
        viewModelScope.launch {
            cities = getCityListUseCase.invoke()
            updateCurrentWeatherList()
            isMetric = userPrefsUseCase.getIsMetric()
            isCelsius = userPrefsUseCase.getIsCelsius()
        }
    }

    fun searchCities(query: String) {
        if(query.isEmpty()) return
        viewModelScope.launch {
            val result = getCitySuggestionsUseCase.invoke(query)
            suggestedCities.postValue(result)
        }
    }

    private suspend fun updateCurrentWeatherList(){
        cities.collect{item ->
            currentWeathers.removeIf {it.first.cityName == item.cityName }
            currentWeathers.add(getCurrentWeatherUseCase.invokeList(item))
        }
    }

    fun addCity(cityItem: CityItem) = viewModelScope.launch {
        addCityUseCase.invoke(cityItem)
        updateCurrentWeatherList()
    }

    fun deleteCity(cityItem: CityItem) = viewModelScope.launch {
        deleteCityUseCase.invoke(cityItem)
        updateCurrentWeatherList()
    }


}