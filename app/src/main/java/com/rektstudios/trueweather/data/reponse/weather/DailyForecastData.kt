package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastData(
    @SerialName("date")
    val dateString: String?,
    @SerialName("date_epoch")
    val dateEpoch: Int?,
    @SerialName("day")
    val dailyWeatherData: DailyWeatherData?,
    @SerialName("hour")
    val hourlyWeatherDataList: List<HourlyWeatherData>?,
)
