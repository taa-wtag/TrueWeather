package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.mapper.toCityData
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import javax.inject.Inject

class GetCitySuggestionsUseCase @Inject constructor(
    private val cityRepository: ICityRepository,
    private val weatherRepository: IWeatherRepository
) {
    suspend fun invoke(query: String, mapbox: Boolean = true ):List<CityItem> =
        if(mapbox) getCityListFromMapbox(query)
        else getCityListFromWeatherApi(query)

    private suspend fun getCityListFromMapbox(query: String):List<CityItem> = cityRepository.searchForPlaces(query).data?.toCityData()?: emptyList()

    private suspend fun getCityListFromWeatherApi(query: String):List<CityItem> = weatherRepository.searchCity(query).data?.toCityData()?: emptyList()
}