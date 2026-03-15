package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ICityRepository {
    suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse>

    suspend fun addCity(city: String)

    suspend fun deleteCity(city: String)

    suspend fun observeCityList(): Flow<List<CityEntity>>

    suspend fun getCityByName(city: String): CityEntity?
}
