package com.rektstudios.trueweather.domain.data

data class HourlyWeatherCardData(
    val timeInHours: String,
    val condition: String,
    val imageUrl: String,
)
