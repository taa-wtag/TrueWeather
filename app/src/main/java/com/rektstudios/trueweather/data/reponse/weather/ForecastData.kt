package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastData(
    @SerialName("forecastday")
    val dailyForecastDataList: List<DailyForecastData>?,
)
