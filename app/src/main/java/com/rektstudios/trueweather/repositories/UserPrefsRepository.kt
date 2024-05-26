package com.rektstudios.trueweather.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPrefsRepository @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
): IPrefsDataStoreRepository {
    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        prefsDataStore.edit {
            it[key] = value
        }
    }

    override suspend fun readValue(key: Preferences.Key<String>): String {
        val result = prefsDataStore.data.first()
        return result[key]?:""
    }

    companion object{
        val KEY_UUID = stringPreferencesKey("uuid")
    }
}