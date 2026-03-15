package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherData(
    @SerialName("avghumidity")
    val avgHumidity: Int?,
    @SerialName("avgtemp_c")
    val avgTempC: Double?,
    @SerialName("avgtemp_f")
    val avgTempF: Double?,
    @SerialName("avgvis_km")
    val avgVisKm: Double?,
    @SerialName("avgvis_miles")
    val avgVisMiles: Double?,
    @SerialName("condition")
    val weatherCondition: WeatherCondition?,
    @SerialName("maxtemp_c")
    val maxTempC: Double?,
    @SerialName("maxtemp_f")
    val maxTempF: Double?,
    @SerialName("maxwind_kph")
    val maxWindKph: Double?,
    @SerialName("maxwind_mph")
    val maxWindMph: Double?,
    @SerialName("mintemp_c")
    val minTempC: Double?,
    @SerialName("mintemp_f")
    val minTempF: Double?,
)
