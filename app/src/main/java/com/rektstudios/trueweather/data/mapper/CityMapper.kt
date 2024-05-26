package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.remote.reponses.mapbox.Suggestion

fun Suggestion.toCityData(): CityItem{
    val city = this
    return CityItem().apply {
        cityName = city.name?:""
        formattedName = city.placeFormatted?:""
        country = city.context?.country?.name ?: ""
        countryCode = city.context?.country?.countryCode ?: ""
        language = city.language?:""
    }
}

