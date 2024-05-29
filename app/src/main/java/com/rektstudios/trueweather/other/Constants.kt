package com.rektstudios.trueweather.other

import androidx.datastore.preferences.core.stringPreferencesKey
import java.util.Calendar
import java.util.UUID

object Constants {
    const val WEATHER_BASE_URL = "https://api.weatherapi.com/v1"
    const val MAPBOX_BASE_URL = "https://api.mapbox.com"

    const val SEARCH_LIMIT = 5
    const val FORECAST_MAX_DAYS = 14


    val USER_UUID = UUID.randomUUID().toString()

    enum class WeatherCondition {
        Cloudy,
        Sunny,
        Rainy,
        Snowy,
        Hail,
        Overcast,
        PartCloudy,
        PartRainy,
        PartSnowy,
        PartHail,
        RainAndThunder,
        SnowAndThunder,
        Thunder,
        Blizzard,
        Foggy,
        None
    }
    fun getDateEpochToday(): Long{
        val dateEpoch = getTimeEpochNow()
        return dateEpoch-dateEpoch%86400
    }

    fun getTimeEpochNow(): Long{
        return Calendar.getInstance().timeInMillis/1000
    }

    val KEY_CITY_NAME = stringPreferencesKey("city_name")
    val KEY_COUNTRY_NAME = stringPreferencesKey("country_name")
    val KEY_METRIC = stringPreferencesKey("metric")
    val KEY_CELSIUS = stringPreferencesKey("celsius")

}