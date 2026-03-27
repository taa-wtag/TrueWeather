package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.domain.mapper.toHourlyWeatherItem
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import com.rektstudios.trueweather.domain.util.TimeUtil.Companion.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetCurrentWeatherUseCase
    @Inject
    constructor(
        private val weatherRepository: IWeatherRepository,
    ) {
        suspend operator fun invoke(city: String): Flow<HourlyWeatherEntity?> {
            val current = getWeatherFromCache(city)
            current.firstOrNull()?.let {
                if (it.timeEpoch.minus(getCurrentTime()) < FORECAST_MIN_TIME_PAST) {
                    return current
                }
            }
            return getWeatherFromRemote(city)
        }

        private suspend fun getWeatherFromCache(city: String) = weatherRepository.getCurrentWeatherFromCache(city)

        private suspend fun getWeatherFromRemote(city: String): Flow<HourlyWeatherEntity?> {
            weatherRepository
                .getCurrentWeatherFromRemote(city)
                .data
                ?.currentWeatherData
                ?.toHourlyWeatherItem(city)
                ?.let { weatherRepository.addWeather(city, it) }
            return getWeatherFromCache(city)
        }
    }
