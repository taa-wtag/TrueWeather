package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.domain.location.ILocationTracker
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import javax.inject.Inject

class CurrentCityUseCase @Inject constructor(
    private val cityRepository: ICityRepository,
    private val prefsRepository: IPrefsRepository,
    private val locationTracker: ILocationTracker,
    private val getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
) {
    suspend fun getCurrentCity(): CityItem {
        var city = ""
        locationTracker.getCurrentLocation()?.let {
            city = getCityNameFromLocationUseCase.invoke(it.latitude, it.longitude)
        }
        if(city.isEmpty()) {
            city = prefsRepository.readValue(KEY_CITY_NAME)
            cityRepository.addCity(CityItem().apply { cityName = city })
        }
        return cityRepository.getCityByName(city)!!
    }

    suspend fun setCurrentCity(cityItem: CityItem) = prefsRepository.saveValue(KEY_CITY_NAME, cityItem.cityName)
}