package com.rektstudios.trueweather.data.mapper

import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.reponses.weather.Forecastday
import com.rektstudios.trueweather.data.remote.reponses.weather.WeatherHour

fun WeatherHour.toWeatherDataHour(): WeatherHourItem {
    val current = this
    return WeatherHourItem().apply {
            timeEpoch = current.timeEpoch?.toLong() ?: -1
            time = current.time?: ""
            tempC = current.tempC ?: -999.0
            tempF = current.tempF ?: -999.0
            feelsLikeC = current.feelsLikeC ?: -999.0
            feelsLikeF = current.feelsLikeF ?: -999.0
            visKm = current.visKm ?: -1
            visMiles = current.visMiles ?: -1
            windKph = current.windKph ?: -1.0
            windMph = current.windMph ?: -1.0
            humidity = current.humidity ?: -1
            isDay = current.isDay ?: -1
            conditionText = current.condition?.text ?: ""
//        condition = when (current.condition?.text) {
//            "Sunny" -> WeatherCondition.Sunny
//            "Partly cloudy" -> WeatherCondition.PartCloudy
//            "Cloudy" -> WeatherCondition.Cloudy
//            "Overcast" -> WeatherCondition.Overcast
//            "Mist" -> WeatherCondition.Foggy
//            "Patchy rain possible" -> WeatherCondition.PartRainy
//            "Patchy snow possible" -> WeatherCondition.PartSnowy
//            "Patchy sleet possible" -> WeatherCondition.PartHail
//            "Patchy freezing drizzle possible" -> WeatherCondition.PartHail
//            "Thundery outbreaks possible" -> WeatherCondition.Thunder
//            "Blowing snow" -> WeatherCondition.Snowy
//            "Blizzard" -> WeatherCondition.Blizzard
//            "Fog" -> WeatherCondition.Foggy
//            "Freezing fog" -> WeatherCondition.Foggy
//            "Patchy light drizzle" -> WeatherCondition.PartRainy
//            "Light drizzle" -> WeatherCondition.PartRainy
//            "Freezing drizzle" -> WeatherCondition.Rainy
//            "Heavy freezing drizzle" -> WeatherCondition.Rainy
//            "Patchy light rain" -> WeatherCondition.PartRainy
//            "Light rain" -> WeatherCondition.PartRainy
//            "Moderate rain at times" -> WeatherCondition.Rainy
//            "Moderate rain" -> WeatherCondition.Rainy
//            "Heavy rain at times" -> WeatherCondition.Rainy
//            "Heavy rain" -> WeatherCondition.Rainy
//            "Light freezing rain" -> WeatherCondition.PartRainy
//            "Moderate or heavy freezing rain" -> WeatherCondition.Rainy
//            "Light sleet" -> WeatherCondition.PartHail
//            "Moderate or heavy sleet" -> WeatherCondition.Hail
//            "Patchy light snow" -> WeatherCondition.PartSnowy
//            "Light snow" -> WeatherCondition.PartSnowy
//            "Patchy moderate snow" -> WeatherCondition.Snowy
//            "Moderate snow" -> WeatherCondition.Snowy
//            "Patchy heavy snow" -> WeatherCondition.Snowy
//            "Heavy snow" -> WeatherCondition.Snowy
//            "Ice pellets" -> WeatherCondition.Hail
//            "Light rain shower" -> WeatherCondition.PartRainy
//            "Moderate or heavy rain shower" -> WeatherCondition.Rainy
//            "Torrential rain shower" -> WeatherCondition.Rainy
//            "Light sleet showers" -> WeatherCondition.PartHail
//            "Moderate or heavy sleet showers" -> WeatherCondition.Hail
//            "Light snow showers" -> WeatherCondition.PartSnowy
//            "Moderate or heavy snow showers" -> WeatherCondition.Snowy
//            "Light showers of ice pellets" -> WeatherCondition.PartHail
//            "Moderate or heavy showers of ice pellets" -> WeatherCondition.Hail
//            "Patchy light rain with thunder" -> WeatherCondition.RainAndThunder
//            "Moderate or heavy rain with thunder" -> WeatherCondition.RainAndThunder
//            "Patchy light snow with thunder" -> WeatherCondition.SnowAndThunder
//            "Moderate or heavy snow with thunder" -> WeatherCondition.SnowAndThunder
//
//            else -> WeatherCondition.None
//        }
    }
}

fun Forecastday.toWeatherDataHour(): List<WeatherHourItem> {
    val hours = this.hour
    return hours?.map {
        it.toWeatherDataHour()
    } ?: emptyList()
}

fun Forecastday.toWeatherDataDay(): WeatherDayItem {
    val parent = this
    val current = this.day
    return WeatherDayItem().apply {
        if (current != null) {
            dateEpoch = parent.dateEpoch?.toLong() ?: -1
            date = parent.date ?: ""
            maxTempC = current.maxTempC ?: -999.0
            maxTempF = current.maxTempF ?: -999.0
            minTempC = current.minTempC ?: -999.0
            minTempF = current.minTempF ?: -999.0
            avgTempC = current.avgTempC ?: -999.0
            avgTempF = current.avgTempF ?: -999.0
            avgVisKm = current.avgVisKm ?: -1.0
            avgVisMiles = current.avgVisMiles ?: -1.0
            maxWindKph = current.maxWindKph ?: -1.0
            maxWindMph = current.maxWindMph ?: -1.0
            avgHumidity = current.avgHumidity ?: -1
            conditionText = current.condition?.text ?: ""
        }
    }
}

