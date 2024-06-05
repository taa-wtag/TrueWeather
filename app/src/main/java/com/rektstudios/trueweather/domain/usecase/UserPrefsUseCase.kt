package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import javax.inject.Inject

class UserPrefsUseCase @Inject constructor(
    private val prefsRepository: IPrefsRepository,
) {
    suspend fun getIsMetric() = prefsRepository.readValue(KEY_METRIC).toBoolean()
    suspend fun getIsCelsius() = prefsRepository.readValue(KEY_CELSIUS).toBoolean()
    suspend fun toggleMetric() = prefsRepository.saveValue(KEY_METRIC, getIsMetric().not().toString())
    suspend fun toggleCelsius() = prefsRepository.saveValue(KEY_CELSIUS, getIsCelsius().not().toString())
}