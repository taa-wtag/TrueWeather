package com.rektstudios.trueweather.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithWeatherDetails(
    @Embedded val city: CityEntity,
    @Relation(
        parentColumn = "cityName",
        entityColumn = "cityName",
    )
    val dailyWeather: List<DailyWeatherEntity>,
    @Relation(
        parentColumn = "cityName",
        entityColumn = "cityName",
    )
    val hourlyWeather: List<HourlyWeatherEntity>,
)
