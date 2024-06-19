package com.rektstudios.trueweather.domain.util

import androidx.datastore.preferences.core.stringPreferencesKey
import java.util.UUID

object Constants {
    const val WEATHER_BASE_URL = "https://api.weatherapi.com/v1/"
    const val MAPBOX_BASE_URL = "https://api.mapbox.com/"
    const val SERVER_ERROR_MESSAGE = "Couldn't reach the server. Check your internet connection"
    const val USER_PREFERENCES = "user_preferences"

    const val SEARCH_LIMIT = 5
    const val FORECAST_MAX_DAYS = 3
    const val FORECAST_MAX_DAYS_ALLOWED = 10
    const val FORECAST_MIN_TIME_PAST = 901
    const val SEARCH_TIME_DELAY = 500L
    const val CITY_GRID_SPAN = 2
    const val MAX_BACKGROUND_COUNT = 6


    val USER_UUID = UUID.randomUUID().toString()

    val KEY_CITY_NAME = stringPreferencesKey("city_name")
    val KEY_METRIC = stringPreferencesKey("metric")
    val KEY_CELSIUS = stringPreferencesKey("celsius")

}