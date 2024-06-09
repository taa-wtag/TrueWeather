package com.rektstudios.trueweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
): IPrefsRepository {
    override suspend fun saveValue(key: String, value: String) {
        withContext(Dispatchers.IO) {
            prefsDataStore.edit { it[stringPreferencesKey(key)] = value }
        }
    }


    override suspend fun getObservableValue(key: String): Flow<String?> = withContext(Dispatchers.IO){
        checkValueExists(key)
        prefsDataStore.data.map { it[stringPreferencesKey(key)] }

    }

    private suspend fun checkValueExists(key: String){
        val prefsKey = stringPreferencesKey(key)
        when(key){
            KEY_CITY_NAME -> prefsDataStore.data.firstOrNull()?.get(prefsKey) ?: saveValue(key,"")
            KEY_METRIC -> prefsDataStore.data.firstOrNull()?.get(prefsKey) ?:saveValue(key,"true")
            KEY_CELSIUS -> prefsDataStore.data.firstOrNull()?.get(prefsKey) ?:saveValue(key,"true")
        }
    }
}