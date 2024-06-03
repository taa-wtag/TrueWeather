package com.rektstudios.trueweather.data.repository

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull

class FakePrefsRepository:IPrefsRepository {
    private val metric = MutableLiveData<String>()
    private val celsius = MutableLiveData<String>()
    private val cityName = MutableLiveData<String>()

    override suspend fun saveValue(key: Preferences.Key<String>, value: String) {
        when (key){
            KEY_METRIC -> metric.postValue(value)
            KEY_CELSIUS -> celsius.postValue(value)
            KEY_CITY_NAME -> cityName.postValue(value)
        }
    }

    override suspend fun getObservableValue(key: Preferences.Key<String>): Flow<String> {
        checkValueExists(key)
        return when (key){
            KEY_METRIC -> metric.asFlow()
            KEY_CELSIUS -> celsius.asFlow()
            KEY_CITY_NAME -> cityName.asFlow()
            else -> MutableStateFlow("")
        }
    }
    private suspend fun checkValueExists(key: Preferences.Key<String>){
        when(key){
            KEY_CITY_NAME -> cityName.value?: saveValue(key,"Dhaka, Bangladesh")
            KEY_METRIC -> metric.value?:saveValue(key,"true")
            KEY_CELSIUS -> celsius.value?:saveValue(key,"true")
        }
    }
}