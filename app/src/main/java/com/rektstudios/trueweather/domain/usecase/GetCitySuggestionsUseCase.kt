package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.mapper.toListCityName
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import javax.inject.Inject

class GetCitySuggestionsUseCase @Inject constructor(
    private val cityRepository: ICityRepository,
    private val weatherRepository: IWeatherRepository
) {
    suspend operator fun invoke(query: String, mapbox: Boolean = true ): List<String> =
        if(mapbox) getCityListFromMapbox(query)
        else getCityListFromWeatherApi(query)

    private suspend fun getCityListFromMapbox(query: String):List<String> = cityRepository.searchForPlaces(query).data?.toListCityName()?: emptyList()

    private suspend fun getCityListFromWeatherApi(query: String):List<String> = weatherRepository.searchCity(query).data?.toListCityName()?: emptyList()
}