package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    @SerializedName("current")
    val currentWeatherData: HourlyWeatherData?
)