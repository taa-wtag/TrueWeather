package com.rektstudios.trueweather.domain.util

import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity

class WeatherFormatUtil(
    private val dailyWeatherItems: List<DailyWeatherEntity>,
    private val hourlyWeatherItems: List<HourlyWeatherEntity>,
) {
    fun formatWeather(): List<Pair<DailyWeatherEntity, List<HourlyWeatherEntity>>> {
        val hourItems = hourlyWeatherItems.sortedBy { it.timeEpoch }
        return dailyWeatherItems
            .sortedBy {
                it.dateEpoch
            }.map { day ->
                Pair(
                    day,
                    hourItems.filter {
                        it.timeEpoch?.let { it1 -> day.dateEpoch?.let { it2 -> it1 >= it2 } } == true &&
                            it.timeEpoch?.let { it1 -> day.dateEpoch?.let { it2 -> it1 < it2 + 86400 } } == true
                    },
                )
            }
    }
}
