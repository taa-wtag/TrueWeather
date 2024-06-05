package com.rektstudios.trueweather.data.reponse.weather

data class ForecastWeatherResponse(
    val current: WeatherHour?,
    val forecast: Forecast?
)