package com.rektstudios.trueweather.repositories

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

interface IPrefsRepository {
    suspend fun saveValue(key: Preferences.Key<String>, value: String)
    suspend fun readValue(key: Preferences.Key<String>): String
}