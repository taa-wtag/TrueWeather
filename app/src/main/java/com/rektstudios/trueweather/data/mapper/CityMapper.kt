package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse

fun SearchResponse.toCityData(): List<CityItem> =
    suggestions?.map {city ->
        CityItem().apply {
            val name = (city.name ?: "") +", "+ (city.context?.country?.name ?: "")
            cityName = name
        }
    } ?: emptyList()

fun PlaceResponse.toCityData(): List<CityItem> =
    map { city ->
        CityItem().apply {
            val name = (city.name?: "") +", "+ (city.country?: "")
            cityName = name
        }
    }

