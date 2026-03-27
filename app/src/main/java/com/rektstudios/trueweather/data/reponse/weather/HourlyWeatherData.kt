package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class HourlyWeatherData(
    @SerialName("condition")
    val weatherCondition: WeatherCondition?,
    @SerialName("feelslike_c")
    val feelsLikeC: Double?,
    @SerialName("feelslike_f")
    val feelsLikeF: Double?,
    val humidity: Int?,
    @SerialName("is_day")
    val isDay: Int?,
    @SerialName("temp_c")
    val tempC: Double?,
    @SerialName("temp_f")
    val tempF: Double?,
    @JsonNames("last_updated")
    val time: String?,
    @JsonNames("last_updated_epoch", "time_epoch")
    val timeEpoch: Int?,
    @SerialName("vis_km")
    val visKm: Double?,
    @SerialName("vis_miles")
    val visMiles: Double?,
    @SerialName("wind_kph")
    val windKph: Double?,
    @SerialName("wind_mph")
    val windMph: Double?,
)

