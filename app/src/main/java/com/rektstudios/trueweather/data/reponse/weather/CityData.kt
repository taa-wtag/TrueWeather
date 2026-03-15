package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityData(
    @SerialName("country")
    val countryName: String?,
    @SerialName("name")
    val cityName: String?,
)
