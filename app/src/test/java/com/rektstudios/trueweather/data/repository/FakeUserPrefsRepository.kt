package com.rektstudios.trueweather.data.repository

import androidx.datastore.preferences.core.Preferences
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC

class FakeUserPrefsRepository:IPrefsRepository {
    var metric = true
    var celcius = true
    var cityName = "Dhaka, Bangladesh"
    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        when (key){
            KEY_METRIC -> metric = value.toBoolean()
            KEY_CELSIUS -> celcius = value.toBoolean()
            KEY_CITY_NAME -> cityName = value
        }
    }

    override suspend fun readValue(key: Preferences.Key<String>): String {
        return when (key){
            KEY_METRIC -> metric.toString()
            KEY_CELSIUS -> celcius.toString()
            KEY_CITY_NAME -> cityName
            else -> ""
        }
    }
}