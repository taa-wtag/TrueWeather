package com.rektstudios.trueweather.repositories

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.mapper.toCityData
import com.rektstudios.trueweather.data.remote.MapBoxApiService
import com.rektstudios.trueweather.data.remote.reponses.mapbox.SearchResponse
import com.rektstudios.trueweather.other.Constants.USER_UUID
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val mapBoxApiService: MapBoxApiService,
    private val realmDao: IRealmDao
): ICityRepository {
    private suspend fun getSuggestionsFromApi(searchQuery: String, country: String = ""): Resource<SearchResponse> {
        return try {
            val response = mapBoxApiService.searchPlacesSuggest(searchQuery, sessionToken = USER_UUID, country = country)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch(e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse> {
        return getSuggestionsFromApi(searchQuery)
    }

    override suspend fun addCity(cityItem: CityItem) {
        realmDao.addCity(cityItem)
    }

    override suspend fun addCity(city: String, country: String) {
        var cityList: List<CityItem>? = null
        withContext(Dispatchers.IO) {
            val result = getSuggestionsFromApi(city, country)
            cityList = result.data?.suggestions?.map {
                it.toCityData()
            }
        }
        cityList?.forEach { addCity(it) }
    }

    override suspend fun deleteCity(cityItem: CityItem) {
        realmDao.deleteCity(cityItem)
    }

    override suspend fun observeCityList(): Flow<CityItem> {
        return realmDao.getCityList()
    }
}