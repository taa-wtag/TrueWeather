package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.domain.mapper.toDailyWeatherItem
import com.rektstudios.trueweather.domain.mapper.toHourlyWeatherItem
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.domain.util.TimeUtil.Companion.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetForecastWeatherUseCase
    @Inject
    constructor(
        private val weatherRepository: IWeatherRepository,
    ) {
        suspend fun getWeatherHour(city: String): Flow<List<HourlyWeatherEntity>> {
            val current = getWeatherHourFromCache(city)
            current
                .firstOrNull()
                ?.takeIf { it.isNotEmpty() }
                ?.let { return current }
            getWeatherFromRemote(city)
            return getWeatherHourFromCache(city)
        }

        suspend fun getWeatherDay(city: String): Flow<List<DailyWeatherEntity>> {
            val current = getWeatherDayFromCache(city)
            current
                .firstOrNull()
                ?.takeIf { it.isNotEmpty() && it.size >= FORECAST_MAX_DAYS - 1 }
                ?.let { return current }
            getWeatherFromRemote(city)
            return getWeatherDayFromCache(city)
        }

        private suspend fun getWeatherDayFromCache(city: String) =
            weatherRepository
                .getWeatherForecastInDaysFromCache(city)
                .map { list ->
                    list.filter { day -> day.dateEpoch?.minus(getCurrentTime())?.let { it > 0 } ?: false }
                }

        private suspend fun getWeatherHourFromCache(city: String) =
            weatherRepository
                .getWeatherForecastInHoursFromCache(city)
                .map { list -> list.filter { it.timeEpoch.minus(getCurrentTime()) > 0 } }

        suspend fun getWeatherFromRemote(city: String) {
            val weather = weatherRepository.getForecastWeatherFromRemote(city).data
            weather
                ?.currentWeatherData
                ?.toHourlyWeatherItem(city)
                ?.let { weatherRepository.addWeather(city, it) }
            weather
                ?.forecastData
                ?.dailyForecastDataList
                ?.flatMap { it.toHourlyWeatherItem(city) }
                ?.forEach { hour ->
                    weatherRepository.addWeather(city, hour)
                }
            weather
                ?.forecastData
                ?.dailyForecastDataList
                ?.mapNotNull { it.toDailyWeatherItem(city) }
                ?.forEach { day ->
                    weatherRepository.addWeather(city, day)
                }
        }
    }
