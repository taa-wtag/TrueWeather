package com.rektstudios.trueweather.domain.repository

import androidx.datastore.preferences.core.Preferences

interface IPrefsRepository {
    suspend fun saveValue(key: Preferences.Key<String>, value: String)
    suspend fun readValue(key: Preferences.Key<String>): String
}