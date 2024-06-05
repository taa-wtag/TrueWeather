package com.rektstudios.trueweather.presentation

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityViewModel @Inject constructor(
    private val addCityUseCase: AddCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val getCitySuggestionsUseCase: GetCitySuggestionsUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
): ViewModel() {
    lateinit var cities: Flow<CityItem> private set
    lateinit var currentWeathers: List<Pair<CityItem,Flow<WeatherHourItem?>>> private set
    lateinit var suggestedCities: MutableLiveData<List<CityItem>> private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cities = getCityListUseCase.invoke()
            currentWeathers = getCurrentWeatherUseCase.invoke(cities.toList())
        }
    }

    fun searchCities(query: String) {
        if(query.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCitySuggestionsUseCase.invoke(query)
            suggestedCities.postValue(result)
        }
    }

    fun addCity(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        addCityUseCase.invoke(cityItem)
        currentWeathers = getCurrentWeatherUseCase.invoke(cities.toList())
    }

    fun deleteCity(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        deleteCityUseCase.invoke(cityItem)
        currentWeathers = getCurrentWeatherUseCase.invoke(cities.toList())
    }


}