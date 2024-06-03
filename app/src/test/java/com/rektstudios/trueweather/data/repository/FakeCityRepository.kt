package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.reponse.mapbox.City
import com.rektstudios.trueweather.data.reponse.mapbox.Context
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.mapbox.Suggestion
import com.rektstudios.trueweather.data.reponse.weather.Place
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class FakeCityRepository(private val realmDao: IRealmDao):ICityRepository {
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }
    override suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse> {
        return if(shouldReturnNetworkError || !"Tokyo".contains(searchQuery)){
            Resource.Error("Error", null)
        }
        else{
            Resource.Success(
                SearchResponse(
                    listOf(
                        Suggestion(
                            Context(City("Japan")),
                            "Tokyo"
                        )
                    )
                )
            )
        }
    }

    override suspend fun addCity(cityItem: CityItem) = realmDao.addCity(cityItem)

    override suspend fun deleteCity(cityItem: CityItem) = realmDao.deleteCity(cityItem)

    override suspend fun observeCityList(): Flow<CityItem> = realmDao.getCityList()

    override suspend fun getCityByName(city: String): CityItem? = realmDao.getCity(city).first()


}