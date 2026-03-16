package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.dao.IDatabaseDao
import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.remote.MapBoxApiService
import com.rektstudios.trueweather.data.remote.MapboxQuery
import com.rektstudios.trueweather.data.remote.toMap
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.util.CheckResponseUtil
import com.rektstudios.trueweather.domain.util.Constants.MAX_BACKGROUND_COUNT
import com.rektstudios.trueweather.domain.util.Constants.SERVER_ERROR_MESSAGE
import com.rektstudios.trueweather.domain.util.Constants.USER_UUID
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

class CityRepositoryImpl
    @Inject
    constructor(
        private val mapBoxApiService: MapBoxApiService,
        private val realmDao: IDatabaseDao,
    ) : ICityRepository {
        override suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse> =
            withContext(Dispatchers.IO) {
                try {
                    CheckResponseUtil(
                        mapBoxApiService.searchPlaceSuggestions(
                            MapboxQuery(
                                searchQuery = searchQuery,
                                sessionToken = USER_UUID,
                            ).toMap(),
                        ),
                    ).checkResponse()
                } catch (_: Exception) {
                    Resource.Error(SERVER_ERROR_MESSAGE, null)
                }
            }

        override suspend fun addCity(city: String) {
            realmDao.addCity(CityEntity(cityName = city, backgroundColor = Random().nextInt(MAX_BACKGROUND_COUNT - 1)))
        }

        override suspend fun deleteCity(city: String) {
            realmDao.deleteCity(city)
        }

        override suspend fun observeCityList(): Flow<List<CityEntity>> = realmDao.getCityList()

        override suspend fun getCityByName(city: String): CityEntity? = realmDao.getCity(city)
    }
