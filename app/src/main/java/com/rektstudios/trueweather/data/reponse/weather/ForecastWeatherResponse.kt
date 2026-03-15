package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherResponse(
    @SerialName("current")
    val currentWeatherData: HourlyWeatherData?,
    @SerialName("forecast")
    val forecastData: ForecastData?,
)
