package com.rektstudios.trueweather.domain.data

data class DailyWeatherData(
    val dayOfWeek: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
)
