package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.reponse.mapbox.CountryData
import com.rektstudios.trueweather.data.reponse.mapbox.PlaceData
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.mapbox.CitySuggestion
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow


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
                        CitySuggestion(
                            PlaceData(CountryData("Japan")),
                            "Tokyo"
                        )
                    )
                )
            )
        }
    }

    override suspend fun addCity(city: String) = realmDao.addCity(city)

    override suspend fun deleteCity(city: String) = realmDao.deleteCity(city)

    override suspend fun observeCityList(): Flow<List<CityItem>> = realmDao.getCityList()

    override suspend fun getCityByName(city: String): CityItem? = realmDao.getCity(city)


}