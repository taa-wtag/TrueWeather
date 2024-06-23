package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.repository.ICityRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val cityRepository: ICityRepository
) {

    suspend operator fun invoke(city: String) = cityRepository.deleteCity(city)

}