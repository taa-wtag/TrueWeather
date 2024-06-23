package com.rektstudios.trueweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
) : IPrefsRepository {
    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        withContext(Dispatchers.IO) {
            prefsDataStore.edit { it[key] = value }
        }
    }


    override suspend fun getObservableValue(key: Preferences.Key<String>): Flow<String?> =
        withContext(Dispatchers.IO) {
            prefsDataStore.data.map { it[key] }
        }
}