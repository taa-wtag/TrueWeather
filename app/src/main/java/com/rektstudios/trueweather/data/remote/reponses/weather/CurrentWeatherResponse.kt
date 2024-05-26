package com.rektstudios.trueweather.data.remote.reponses.weather

data class CurrentWeatherResponse(
    val current: Current?,
    val location: Location?
)