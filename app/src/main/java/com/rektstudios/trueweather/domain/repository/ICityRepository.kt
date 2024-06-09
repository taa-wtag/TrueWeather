package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse>
    suspend fun addCity(city: String)
    suspend fun deleteCity(city: String)
    suspend fun observeCityList(): Flow<List<CityItem>>
    suspend fun getCityByName(city: String): CityItem?
}