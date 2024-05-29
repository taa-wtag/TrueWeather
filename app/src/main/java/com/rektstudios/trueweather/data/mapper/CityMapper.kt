package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.remote.reponses.mapbox.SearchResponse

fun SearchResponse.toCityData(): List<CityItem>{
    return suggestions?.map {
        val city = it
        CityItem().apply {
            val name = (city.name ?: "") +", "+ (city.context?.country?.name ?: "")
            cityName = name
        }
    } ?: emptyList()
}

