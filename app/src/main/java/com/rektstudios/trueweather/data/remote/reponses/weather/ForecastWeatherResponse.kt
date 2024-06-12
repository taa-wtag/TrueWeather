package com.rektstudios.trueweather.data.remote.reponses.weather

data class ForecastWeatherResponse(
    val current: Current?,
    val forecast: Forecast?,
    val location: Location?
)