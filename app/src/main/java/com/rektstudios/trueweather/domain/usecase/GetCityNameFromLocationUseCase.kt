package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.helper.IGeocodeHelper
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCityNameFromLocationUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    private val geocodeHelper: IGeocodeHelper
) {
    suspend fun invoke(lat: Double, lon: Double): String {
        var cityName = ""
            cityName = geocodeHelper.geocodeLocation(lat, lon)
        if (cityName.isEmpty()) {
                cityName = fetchLocationFromApi(lat, lon)
        }
        return cityName
    }

    private suspend fun fetchLocationFromApi(lat: Double, lon: Double): String {
        return weatherRepository.getCityNameFromRemote(lat, lon).data?.let {item ->
            item.firstOrNull()?.let {
                it.name + ", " + it.country
            }
        }?:""
    }
}