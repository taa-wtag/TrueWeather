package com.rektstudios.trueweather.domain.util

import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem

class WeatherFormatUtil(
    private val weatherDayItems: List<WeatherDayItem>,
    private val weatherHourItems: List<WeatherHourItem>
) {
    fun formatWeather(): List<Pair<WeatherDayItem, List<WeatherHourItem>>>{
        val hourItems = weatherHourItems.sortedBy { it.timeEpoch }
        return weatherDayItems.sortedBy {
            it.dateEpoch
        }.map { day->
            Pair(day, hourItems.filter {
                it.timeEpoch >= day.dateEpoch &&
                        it.timeEpoch<(day.dateEpoch+86400)
            })
        }
    }
}