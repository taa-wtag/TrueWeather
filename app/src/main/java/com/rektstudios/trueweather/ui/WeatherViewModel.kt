package com.rektstudios.trueweather.ui

import com.rektstudios.trueweather.repositories.ICityRepository
import com.rektstudios.trueweather.repositories.IWeatherRepository

class WeatherViewModel(
    private val cityRepository: ICityRepository,
    private val weatherRepository: IWeatherRepository
) {
}