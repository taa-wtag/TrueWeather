package com.rektstudios.trueweather.data.remote.reponses.weather

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int?,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int?,
    val cloud: Int?,
    val condition: Condition?,
    @SerializedName("dewpoint_c")
    val dewpointC: Double?,
    @SerializedName("dewpoint_f")
    val dewpointF: Double?,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double?,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double?,
    @SerializedName("gust_kph")
    val gustKph: Double?,
    @SerializedName("gust_mph")
    val gustMph: Double?,
    @SerializedName("heatindex_c")
    val heatIndexC: Double?,
    @SerializedName("heatindex_f")
    val heatIndexF: Double?,
    val humidity: Int?,
    @SerializedName("is_day")
    val isDay: Int?,
    @SerializedName("precip_in")
    val precipIn: Int?,
    @SerializedName("precip_mm")
    val precipMm: Int?,
    @SerializedName("pressure_in")
    val pressureIn: Double?,
    @SerializedName("pressure_mb")
    val pressureMb: Int?,
    @SerializedName("snow_cm")
    val snowCm: Int?,
    @SerializedName("temp_c")
    val tempC: Double?,
    @SerializedName("temp_f")
    val tempF: Double?,
    val time: String?,
    @SerializedName("time_epoch")
    val timeEpoch: Int?,
    val uv: Int?,
    @SerializedName("vis_km")
    val visKm: Int?,
    @SerializedName("vis_miles")
    val visMiles: Int?,
    @SerializedName("will_it_rain")
    val willItRain: Int?,
    @SerializedName("will_it_snow")
    val willItSnow: Int?,
    @SerializedName("wind_degree")
    val windDegree: Int?,
    @SerializedName("wind_dir")
    val windDir: String?,
    @SerializedName("wind_kph")
    val windKph: Double?,
    @SerializedName("wind_mph")
    val windMph: Double?,
    @SerializedName("windchill_c")
    val windChillC: Double?,
    @SerializedName("windchill_f")
    val windChillF: Double?
)