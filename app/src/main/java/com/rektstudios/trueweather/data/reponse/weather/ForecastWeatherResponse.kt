package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("current")
    val currentWeatherData: HourlyWeatherData?,

    @SerializedName("forecast")
    val forecastData: ForecastData?
)