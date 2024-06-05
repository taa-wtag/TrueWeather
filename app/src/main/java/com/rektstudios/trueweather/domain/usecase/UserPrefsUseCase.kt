package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.util.Constants.KEY_CELSIUS
import com.rektstudios.trueweather.domain.util.Constants.KEY_METRIC
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefsUseCase @Inject constructor(
    private val prefsRepository: IPrefsRepository,
) {
    suspend fun getIsMetric() = prefsRepository.getObservableValue(KEY_METRIC).map { it.toBoolean() }
    suspend fun getIsCelsius() = prefsRepository.getObservableValue(KEY_CELSIUS).map { it.toBoolean() }
    suspend fun setMetric(value: Boolean) = prefsRepository.saveValue(KEY_METRIC, value.toString())
    suspend fun setCelsius(value: Boolean) = prefsRepository.saveValue(KEY_CELSIUS, value.toString())
}