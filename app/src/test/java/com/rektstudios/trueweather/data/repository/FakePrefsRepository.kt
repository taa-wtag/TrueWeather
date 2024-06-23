package com.rektstudios.trueweather.data.repository

import androidx.datastore.preferences.core.Preferences
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

class FakePrefsRepository : IPrefsRepository {
    private val metric = MutableStateFlow("true")
    private val celsius = MutableStateFlow("true")
    private val cityName = MutableStateFlow("Tokyo, Japan")

    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        when (key) {
            KEY_METRIC -> metric.emit(value)
            KEY_CELSIUS -> celsius.emit(value)
            KEY_CITY_NAME -> cityName.emit(value)
        }
    }

    override suspend fun getObservableValue(key: Preferences.Key<String>): Flow<String> {
        return when (key) {
            KEY_METRIC -> metric
            KEY_CELSIUS -> celsius
            KEY_CITY_NAME -> cityName
            else -> emptyFlow()
        }
    }
}