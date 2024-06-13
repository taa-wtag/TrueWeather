package com.rektstudios.trueweather.domain.mapper

import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse

fun SearchResponse.toListCityName(): List<String> =
    citySuggestions?.map { city ->
        city.cityName + ", "+city.placeData?.countryData?.countryName
    } ?: emptyList()

fun PlaceResponse.toListCityName(): List<String> =
    mapNotNull {city ->
        city.cityName + ", " +city.countryName
    }

