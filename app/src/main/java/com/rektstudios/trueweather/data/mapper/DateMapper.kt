package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.DateItem
import com.rektstudios.trueweather.data.remote.reponses.weather.Forecastday

fun Forecastday.toDateData(cityItem: CityItem): DateItem{
    val day = this
    return DateItem().apply {
        this.cityItem = cityItem
        dateEpoch = day.dateEpoch?.toLong()?:-1
        dateText = day.date ?:""
        weatherThisDay = day.toWeatherDataDay()
        day.toWeatherDataHour().forEach { weatherEveryHour.add(it)}
    }
}

