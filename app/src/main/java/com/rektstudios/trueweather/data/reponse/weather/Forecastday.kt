package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class Forecastday(
    val date: String?,
    @SerializedName("date_epoch")
    val dateEpoch: Int?,
    val day: WeatherDay?,
    val hour: List<WeatherHour>?
)