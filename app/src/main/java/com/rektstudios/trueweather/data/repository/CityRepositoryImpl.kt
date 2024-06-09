package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.remote.MapBoxApiService
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.util.CheckResponseUtil
import com.rektstudios.trueweather.domain.util.Constants.SERVER_ERROR_MESSAGE
import com.rektstudios.trueweather.domain.util.Constants.USER_UUID
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val mapBoxApiService: MapBoxApiService,
    private val realmDao: IRealmDao
): ICityRepository {

    override suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse> = withContext(Dispatchers.IO){
        try {
            CheckResponseUtil(mapBoxApiService.searchPlacesSuggest(searchQuery, USER_UUID)).checkResponse()
        } catch(e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}
    }

    override suspend fun addCity(city: String) {realmDao.addCity(city)}

    override suspend fun deleteCity(city: String) {realmDao.deleteCity(city)}

    override suspend fun observeCityList(): Flow<List<CityItem>> = realmDao.getCityList()

    override suspend fun getCityByName(city: String): CityItem? = realmDao.getCity(city)
}