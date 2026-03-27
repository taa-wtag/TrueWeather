package com.rektstudios.trueweather.domain.data

data class CityState(
    val hourlyWeatherCardData: List<HourlyWeatherCardData> = emptyList(),
    val dailyWeatherData: List<DailyWeatherData> = emptyList(),
)
