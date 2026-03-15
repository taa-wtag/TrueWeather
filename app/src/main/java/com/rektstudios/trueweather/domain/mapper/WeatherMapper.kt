package com.rektstudios.trueweather.domain.mapper

import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.data.reponse.weather.DailyForecastData
import com.rektstudios.trueweather.data.reponse.weather.HourlyWeatherData

fun HourlyWeatherData.toHourlyWeatherItem(cityName: String): HourlyWeatherEntity =
    HourlyWeatherEntity(
        0,
        cityName,
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

fun DailyForecastData.toHourlyWeatherItem(cityName: String): List<HourlyWeatherEntity> =
    this.hourlyWeatherDataList?.map {
        it.toHourlyWeatherItem(cityName)
    } ?: emptyList()

fun DailyForecastData.toDailyWeatherItem(cityName: String): DailyWeatherEntity? =
    this.dailyWeatherData?.let {
        DailyWeatherEntity(
            0,
            cityName,
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
            "https:" + it.weatherCondition?.icon,
        )
    }
