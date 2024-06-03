package com.rektstudios.trueweather.domain.repository

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface IPrefsRepository {
    suspend fun saveValue(key: Preferences.Key<String>, value: String)
    suspend fun getObservableValue(key: Preferences.Key<String>): Flow<String?>
}