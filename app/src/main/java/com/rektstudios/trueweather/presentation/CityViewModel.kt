package com.rektstudios.trueweather.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val userPrefsUseCase: UserPrefsUseCase,
    private val addCityUseCase: AddCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val getCitySuggestionsUseCase: GetCitySuggestionsUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
): ViewModel() {
    val cities = MutableStateFlow<List<CityItem>>(emptyList())
    var suggestedCities =  MutableLiveData<List<CityItem>>(emptyList())
        private set
    val isMetric = MutableStateFlow<Boolean?>(null)
    val isCelsius = MutableStateFlow<Boolean?>(null)

    init {
        viewModelScope.launch {
            launch { getCityListUseCase().collect{cities.value=it}}
            launch { userPrefsUseCase.getIsCelsius().collect(isCelsius)}
            launch { userPrefsUseCase.getIsMetric().collect(isMetric)}
        }
    }

    fun searchCities(query: String) {
        if(query.isEmpty()) return
        viewModelScope.launch {
            val result = getCitySuggestionsUseCase(query)
            suggestedCities.postValue(result)
        }
    }


    fun addCity(city: String) = viewModelScope.launch {
        addCityUseCase(city)
        getCurrentWeatherUseCase(city)
    }

    fun deleteCity(city: String) = viewModelScope.launch {
        deleteCityUseCase(city)
    }


}