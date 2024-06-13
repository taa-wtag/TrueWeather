package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.helper.IGeocodeHelper
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import javax.inject.Inject

class GetCityNameFromLocationUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    private val geocodeHelper: IGeocodeHelper
) {
    suspend operator fun invoke(lat: Double, lon: Double): String {
        var cityName = geocodeHelper.geocodeLocation(lat, lon)
        if (cityName.isEmpty()) {
                cityName = fetchLocationFromApi(lat, lon)
        }
        return cityName
    }

    private suspend fun fetchLocationFromApi(lat: Double, lon: Double): String {
        return weatherRepository.getCityNameFromRemote(lat, lon).data?.let {item ->
            item.firstOrNull()?.let {
                it.cityName + ", " + it.countryName
            }
        }?:""
    }
}