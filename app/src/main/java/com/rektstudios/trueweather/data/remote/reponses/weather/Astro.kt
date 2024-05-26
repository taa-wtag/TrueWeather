package com.rektstudios.trueweather.data.remote.reponses.weather

import com.google.gson.annotations.SerializedName

data class Astro(
    @SerializedName("is_moon_up")
    val isMoonUp: Int?,
    @SerializedName("is_sun_up")
    val isSunUp: Int?,
    @SerializedName("moon_illumination")
    val moonIllumination: Int?,
    @SerializedName("moon_phase")
    val moonPhase: String?,
    val moonrise: String?,
    val moonset: String?,
    val sunrise: String?,
    val sunset: String?
)