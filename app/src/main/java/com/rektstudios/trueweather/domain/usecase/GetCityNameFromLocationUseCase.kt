package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.helper.IGeocodeHelper
import com.rektstudios.trueweather.domain.mapper.toCityName
import com.rektstudios.trueweather.domain.repository.ICityRepository
import javax.inject.Inject

class GetCityNameFromLocationUseCase
    @Inject
    constructor(
        private val cityRepository: ICityRepository,
        private val geocodeHelper: IGeocodeHelper,
    ) {
        suspend operator fun invoke(
            lat: Double,
            lon: Double,
        ): String {
            var cityName = geocodeHelper.geocodeLocation(lat, lon)
            if (cityName.isEmpty()) {
                cityName = fetchLocationFromApi(lat, lon)
            }
            return cityName
        }

        private suspend fun fetchLocationFromApi(
            lat: Double,
            lon: Double,
        ): String =
            cityRepository
                .reverseGeocodePlaces(lon, lat)
                .data
                ?.features
                ?.firstOrNull()
                ?.citySuggestion
                ?.toCityName()
                .orEmpty()
    }
