package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend operator fun invoke(city: String): Flow<WeatherHourItem?> {
        val current = getWeatherFromCache(city)
        current.firstOrNull()?.let {
            if (it.isValid && (it.timeEpoch - Calendar.getInstance().timeInMillis/1000) < FORECAST_MIN_TIME_PAST)
                return current
        }
        return getWeatherFromRemote(city)
    }

    private suspend fun getWeatherFromCache(city: String) = weatherRepository.getCurrentWeatherFromCache(city)
    private suspend fun getWeatherFromRemote(city: String): Flow<WeatherHourItem?> {
        weatherRepository.getCurrentWeatherFromRemote(city).data?.current?.toWeatherDataHour()?.let {
            weatherRepository.addWeather(city,it)
        }
        return getWeatherFromCache(city)
    }
}