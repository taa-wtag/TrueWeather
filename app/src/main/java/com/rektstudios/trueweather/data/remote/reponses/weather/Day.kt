package com.rektstudios.trueweather.data.remote.reponses.weather

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avghumidity")
    val avgHumidity: Int?,
    @SerializedName("avgtemp_c")
    val avgTempC: Double?,
    @SerializedName("avgtemp_f")
    val avgTempF: Double?,
    @SerializedName("avgvis_km")
    val avgVisKm: Double?,
    @SerializedName("avgvis_miles")
    val avgVisMiles: Double?,
    val condition: Condition?,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int?,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int?,
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int?,
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Int?,
    @SerializedName("maxtemp_c")
    val maxTempC: Double?,
    @SerializedName("maxtemp_f")
    val maxTempF: Double?,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double?,
    @SerializedName("maxwind_mph")
    val maxWindMph: Double?,
    @SerializedName("mintemp_c")
    val minTempC: Double?,
    @SerializedName("mintemp_f")
    val minTempF: Double?,
    @SerializedName("totalprecip_in")
    val totalPrecipIn: Double?,
    @SerializedName("totalprecip_mm")
    val totalPrecipMm: Double?,
    @SerializedName("totalsnow_cm")
    val totalSnowCm: Int?,
    val uv: Int?
)