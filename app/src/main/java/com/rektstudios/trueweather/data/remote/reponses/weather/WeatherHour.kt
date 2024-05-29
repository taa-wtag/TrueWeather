package com.rektstudios.trueweather.data.remote.reponses.weather

import com.google.gson.annotations.SerializedName

data class WeatherHour(
    val condition: Condition?,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double?,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double?,
    val humidity: Int?,
    @SerializedName("is_day")
    val isDay: Int?,
    @SerializedName("temp_c")
    val tempC: Double?,
    @SerializedName("temp_f")
    val tempF: Double?,
    @SerializedName("time", alternate = ["last_updated"])
    val time: String?,
    @SerializedName("time_epoch", alternate = ["last_updated_epoch"])
    val timeEpoch: Int?,
    @SerializedName("vis_km")
    val visKm: Int?,
    @SerializedName("vis_miles")
    val visMiles: Int?,
    @SerializedName("wind_kph")
    val windKph: Double?,
    @SerializedName("wind_mph")
    val windMph: Double?,
)