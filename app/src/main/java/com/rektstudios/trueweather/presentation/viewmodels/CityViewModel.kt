package com.rektstudios.trueweather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.local.entity.toCityCardData
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import com.rektstudios.trueweather.domain.util.Constants.SEARCH_TIME_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class CityViewModel
    @Inject
    constructor(
        private val userPrefsUseCase: UserPrefsUseCase,
        private val addCityUseCase: AddCityUseCase,
        private val deleteCityUseCase: DeleteCityUseCase,
        private val getCityListUseCase: GetCityListUseCase,
        private val getCitySuggestionsUseCase: GetCitySuggestionsUseCase,
        private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    ) : ViewModel() {
        val cityList = MutableStateFlow<List<CityEntity>>(emptyList())
        private val isMetric = MutableStateFlow(true)
        private val isCelsius = MutableStateFlow(true)

        val cityCardDataList =
            combine(
                isCelsius,
                cityList,
            ) { celcius, list ->
                Pair(celcius, list)
            }.flatMapLatest { (celcius, list) ->
                if (list.isEmpty()) return@flatMapLatest flowOf(emptyList())
                combine(list.map { getCurrentWeatherUseCase(it.cityName).onStart { emit(null) } }) { weatherList ->
                    list.map { city -> city.toCityCardData(weatherList.find { it?.cityName == city.cityName }, celcius) }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()
        val suggestedCities =
            _searchQuery
                .debounce(SEARCH_TIME_DELAY)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flowOf(
                        if (query.isBlank()) {
                            emptyList()
                        } else {
                            getCitySuggestionsUseCase(query)
                        },
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList(),
                )

        init {
            viewModelScope.launch {
                launch { getCityListUseCase().collect(cityList) }
                launch { userPrefsUseCase.getIsCelsius().collect(isCelsius) }
                launch { userPrefsUseCase.getIsMetric().collect(isMetric) }
            }
        }

        fun searchCities(query: String) {
            _searchQuery.value = query
        }

        fun addCity(city: String) =
            viewModelScope.launch {
                addCityUseCase(city)
            }

        fun deleteCity(city: String) = viewModelScope.launch { deleteCityUseCase(city) }
    }
