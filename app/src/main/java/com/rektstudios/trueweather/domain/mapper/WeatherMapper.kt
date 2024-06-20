package com.rektstudios.trueweather.domain.mapper

import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.data.reponse.weather.DailyForecastData
import com.rektstudios.trueweather.data.reponse.weather.HourlyWeatherData

fun HourlyWeatherData.toHourlyWeatherItem(): HourlyWeatherItem {
    return HourlyWeatherItem(
        timeEpoch?.toLong(),
        timeString,
        tempC,
        tempF,
        feelsLikeC,
        feelsLikeF,
        visKm,
        visMiles,
        windKph,
        windMph,
        humidity,
        isDay,
        weatherCondition?.text,
        "https:" + weatherCondition?.icon,
    )
}

fun DailyForecastData.toHourlyWeatherItem(): List<HourlyWeatherItem> =
    this.hourlyWeatherDataList?.map {
        it.toHourlyWeatherItem()
    } ?: emptyList()

fun DailyForecastData.toDailyWeatherItem(): DailyWeatherItem? {
    return this.dailyWeatherData?.let {
        DailyWeatherItem(
            this.dateEpoch?.toLong(),
            this.dateString,
            it.minTempC,
            it.minTempF,
            it.maxTempC,
            it.maxTempF,
            it.avgTempC,
            it.avgTempF,
            it.avgVisKm,
            it.avgVisMiles,
            it.maxWindKph,
            it.maxWindMph,
            it.avgHumidity,
            it.weatherCondition?.text,
            "https:" + it.weatherCondition?.icon
        )
    }
}


