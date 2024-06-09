package com.rektstudios.trueweather.domain.repository

import kotlinx.coroutines.flow.Flow

interface IPrefsRepository {
    suspend fun saveValue(key: String, value: String)
    suspend fun getObservableValue(key: String): Flow<String?>
}