package com.rektstudios.trueweather.repositories

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.remote.reponses.mapbox.Country
import com.rektstudios.trueweather.data.remote.reponses.mapbox.SearchResponse
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse>
    suspend fun addCity(cityItem: CityItem)
    suspend fun addCity(city: String, country: String)
    suspend fun deleteCity(cityItem: CityItem)
    suspend fun observeCityList(): Flow<CityItem>
}