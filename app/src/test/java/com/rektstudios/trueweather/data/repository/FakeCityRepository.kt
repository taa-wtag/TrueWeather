package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow


class FakeCityRepository:ICityRepository {
    override suspend fun searchForPlaces(searchQuery: String): Resource<SearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun addCity(cityItem: CityItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCity(cityItem: CityItem) {
        TODO("Not yet implemented")
    }

    override suspend fun observeCityList(): Flow<CityItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityByName(city: String): CityItem? {
        TODO("Not yet implemented")
    }


}