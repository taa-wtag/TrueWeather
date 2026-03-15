package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCondition(
    val icon: String?,
    val text: String?,
)
