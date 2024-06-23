package com.rektstudios.trueweather.domain.util

import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem

class WeatherFormatUtil(
    private val dailyWeatherItems: List<DailyWeatherItem>,
    private val hourlyWeatherItems: List<HourlyWeatherItem>
) {

    fun formatWeather(): List<Pair<DailyWeatherItem, List<HourlyWeatherItem>>> {
        val hourItems = hourlyWeatherItems.sortedBy { it.timeEpoch }
        return dailyWeatherItems.sortedBy {
            it.dateEpoch
        }.map { day ->
            Pair(day, hourItems.filter {
                it.timeEpoch?.let { it1 -> day.dateEpoch?.let { it2 -> it1 >= it2 } } == true &&
                        it.timeEpoch?.let { it1 -> day.dateEpoch?.let { it2 -> it1 < it2 + 86400 } } == true
            })
        }
    }

}