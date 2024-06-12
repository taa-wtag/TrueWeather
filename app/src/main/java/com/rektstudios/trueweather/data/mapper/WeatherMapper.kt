package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.reponse.weather.Forecastday
import com.rektstudios.trueweather.data.reponse.weather.WeatherHour

fun WeatherHour.toWeatherDataHour(): WeatherHourItem {
    return WeatherHourItem(
        timeEpoch?.toLong() ?: -1,
        time?: "",
        tempC ?: -999.0,
        tempF ?: -999.0,
        feelsLikeC ?: -999.0,
        feelsLikeF ?: -999.0,
        visKm ?: -1.0,
        visMiles ?: -1.0,
        windKph ?: -1.0,
        windMph ?: -1.0,
        humidity ?: -1,
        isDay ?: -1,
        condition?.text ?: "",
        condition?.icon?:"",
    )
}

fun Forecastday.toWeatherDataHour(): List<WeatherHourItem> =
    this.hour?.map {
        it.toWeatherDataHour()
    } ?: emptyList()

fun Forecastday.toWeatherDataDay(): WeatherDayItem {
    return this.day?.let {
        WeatherDayItem(
            this.dateEpoch?.toLong() ?: -1,
            this.date ?: "",
            it.minTempC ?: -999.0,
            it.minTempF ?: -999.0,
            it.maxTempC ?: -999.0,
            it.maxTempF ?: -999.0,
            it.avgTempC ?: -999.0,
            it.avgTempF ?: -999.0,
            it.avgVisKm ?: -1.0,
            it.avgVisMiles ?: -1.0,
            it.maxWindKph ?: -1.0,
            it.maxWindMph ?: -1.0,
            it.avgHumidity ?: -1,
            it.condition?.text ?: "",
            it.condition?.icon?:""
    )}?:WeatherDayItem()
}


