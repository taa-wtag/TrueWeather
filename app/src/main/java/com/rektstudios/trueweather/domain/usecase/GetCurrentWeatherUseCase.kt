package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.domain.mapper.toHourlyWeatherItem
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import com.rektstudios.trueweather.domain.util.TimeUtil.Companion.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
                if (it.timeEpoch?.minus(getCurrentTime())?.let { it1 -> it1 < FORECAST_MIN_TIME_PAST } != false) {
                    return current
                }
            }
            return getWeatherFromRemote(city)
        }

        suspend operator fun invoke(cityList: List<CityEntity>): Flow<List<HourlyWeatherEntity?>> {
            val hourlyWeatherItemFlowList = cityList.map { invoke(it.cityName) }
            return combine(hourlyWeatherItemFlowList) { it.toList() }
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
