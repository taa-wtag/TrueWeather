package com.rektstudios.trueweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
): IPrefsRepository {
    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        prefsDataStore.edit {
            it[key] = value
        }
    }

    override suspend fun readValue(key: Preferences.Key<String>): String{
        checkValueExists(key)
        return prefsDataStore.data.first()[key]?:""
    }

    private suspend fun checkValueExists(key: Preferences.Key<String>){
        when(key){
            KEY_CITY_NAME -> prefsDataStore.data.first()[key]?: saveValue(key,"Dhaka, Bangladesh")
            KEY_METRIC -> prefsDataStore.data.first()[key]?:saveValue(key,"true")
            KEY_CELSIUS -> prefsDataStore.data.first()[key]?:saveValue(key,"true")
        }
    }
}