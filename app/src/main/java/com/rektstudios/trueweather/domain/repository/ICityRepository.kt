package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse>
    suspend fun addCity(cityItem: CityItem)
    suspend fun deleteCity(cityItem: CityItem)
    suspend fun observeCityList(): Flow<CityItem>
    suspend fun getCityByName(city: String): CityItem?
}