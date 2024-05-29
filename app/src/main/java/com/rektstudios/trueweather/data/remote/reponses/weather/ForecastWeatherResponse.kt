package com.rektstudios.trueweather.data.remote.reponses.weather

data class ForecastWeatherResponse(
    val current: WeatherHour?,
    val forecast: Forecast?
)