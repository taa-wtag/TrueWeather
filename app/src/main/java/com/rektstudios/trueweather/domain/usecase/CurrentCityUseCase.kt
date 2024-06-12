package com.rektstudios.trueweather.domain.usecase

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
    suspend fun getCurrentCity(): CityItem? {
        val city =  prefsRepository.getObservableValue(KEY_CITY_NAME).firstOrNull()?:""
        if(city.isEmpty()) return null
        cityRepository.addCity(city)
        return cityRepository.getCityByName(city)
    }

    suspend fun getCurrentCityFromLocation(): CityItem?{
        locationTracker.getCurrentLocation()?.let {
            getCityNameFromLocationUseCase.invoke(it.first, it.second).run {
                if(this.isNotEmpty()) setCurrentCity(this)
            }
        }
        return getCurrentCity()
    }

    suspend fun setCurrentCity(city: String) = prefsRepository.saveValue(KEY_CITY_NAME, city)
}