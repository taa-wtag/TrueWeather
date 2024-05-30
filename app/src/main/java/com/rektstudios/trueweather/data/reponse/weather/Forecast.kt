package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<Forecastday>?
)