package com.rektstudios.trueweather.domain.usecase

import android.util.Log
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.domain.location.ILocationTracker
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CurrentCityUseCase @Inject constructor(
    private val cityRepository: ICityRepository,
    private val prefsRepository: IPrefsRepository,
    private val locationTracker: ILocationTracker,
    private val getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
) {
    suspend fun getCurrentCity(isFromLocation: Boolean = false): CityItem? {
        var city = ""
        if(isFromLocation) locationTracker.getCurrentLocation()?.let {
            city = getCityNameFromLocationUseCase.invoke(it.first, it.second)
            if (city.isNotEmpty()) prefsRepository.saveValue(KEY_CITY_NAME,city)
        }
        if(city.isEmpty()) city = prefsRepository.getObservableValue(KEY_CITY_NAME).firstOrNull()?:""
        if(city.isEmpty()) return null
        cityRepository.addCity(city)
        return cityRepository.getCityByName(city)
    }

    suspend fun setCurrentCity(city: String) = prefsRepository.saveValue(KEY_CITY_NAME, city)
}