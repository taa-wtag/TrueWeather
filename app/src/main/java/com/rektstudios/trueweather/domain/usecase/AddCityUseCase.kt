package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.domain.repository.ICityRepository
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val cityRepository: ICityRepository
) {
    suspend fun invoke(cityItem: CityItem) = cityRepository.addCity(cityItem)
}