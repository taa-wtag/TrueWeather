package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse

fun SearchResponse.toListCityName(): List<String> =
    suggestions?.mapNotNull {city ->
        city.name
    } ?: emptyList()

fun PlaceResponse.toListCityName(): List<String> =
    mapNotNull {city ->
        city.name
    }

