package com.rektstudios.trueweather.data.reponse.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer

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
    @Serializable(with = TimeSerializer::class)
    val timeString: String? = null,
    @Serializable(with = EpochSerializer::class)
    val timeEpoch: Int? = null,
    @SerialName("vis_km")
    val visKm: Double?,
    @SerialName("vis_miles")
    val visMiles: Double?,
    @SerialName("wind_kph")
    val windKph: Double?,
    @SerialName("wind_mph")
    val windMph: Double?,
)

object TimeSerializer : JsonTransformingSerializer<String>(String.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element is JsonObject) {
            element["time"] ?: element["last_updated"] ?: kotlinx.serialization.json.JsonNull
        } else {
            element
        }
}

object EpochSerializer : JsonTransformingSerializer<String>(String.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element is JsonObject) {
            element["time_epoch"] ?: element["last_updated_epoch"] ?: kotlinx.serialization.json.JsonNull
        } else {
            element
        }
}
