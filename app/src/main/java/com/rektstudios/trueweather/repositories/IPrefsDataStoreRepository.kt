package com.rektstudios.trueweather.repositories

import androidx.datastore.preferences.core.Preferences

interface IPrefsDataStoreRepository {
    suspend fun saveValue(key: Preferences.Key<String>, value: String)
    suspend fun readValue(key: Preferences.Key<String>): String
}