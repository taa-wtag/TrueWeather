package com.rektstudios.trueweather.domain.mapper

import com.rektstudios.trueweather.data.reponse.mapbox.CitySuggestion
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse

fun CitySuggestion.toCityName(): String = cityName + ", " + placeData?.countryData?.countryName

fun SearchResponse.toListCityName(): List<String> = citySuggestions.map { it.toCityName() }

fun PlaceResponse.toListCityName(): List<String> =
    mapNotNull { city ->
        city.cityName + ", " + city.countryName
    }
