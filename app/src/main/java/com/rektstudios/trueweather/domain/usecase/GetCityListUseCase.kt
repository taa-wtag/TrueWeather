package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.repository.ICityRepository
import javax.inject.Inject

class GetCityListUseCase @Inject constructor(
    private val cityRepository: ICityRepository
) {
    suspend fun invoke() = cityRepository.observeCityList()
}