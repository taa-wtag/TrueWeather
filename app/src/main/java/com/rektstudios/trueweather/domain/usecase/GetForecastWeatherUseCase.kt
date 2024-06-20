package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.domain.mapper.toDailyWeatherItem
import com.rektstudios.trueweather.domain.mapper.toHourlyWeatherItem
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MAX_DAYS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {

    suspend fun getWeatherHour(city: String): Flow<List<HourlyWeatherItem>> {
        val current = getWeatherHourFromCache(city)
        current
            .firstOrNull()
            ?.let { if (it.isNotEmpty()) return current }
        getWeatherFromRemote(city)
        return getWeatherHourFromCache(city)
    }

    suspend fun getWeatherDay(city: String): Flow<List<DailyWeatherItem>> {
        val current = getWeatherDayFromCache(city)
        current
            .firstOrNull()
            ?.let { if (it.isNotEmpty() && it.size >= FORECAST_MAX_DAYS - 1) return current }
        getWeatherFromRemote(city)
        return getWeatherDayFromCache(city)
    }

    private suspend fun getWeatherDayFromCache(city: String) =
        weatherRepository.getWeatherForecastInDaysFromCache(city)

    private suspend fun getWeatherHourFromCache(city: String) =
        weatherRepository.getWeatherForecastInHoursFromCache(city)

    private suspend fun getWeatherFromRemote(city: String) {
        val weather = weatherRepository.getForecastWeatherFromRemote(city).data
        weather
            ?.currentWeatherData
            ?.toHourlyWeatherItem()
            ?.let { weatherRepository.addWeather(city, it) }
        weather
            ?.forecastData
            ?.dailyForecastDataList
            ?.forEach { day ->
            weatherRepository.addWeather(city, day.toHourlyWeatherItem())
        }
        weather
            ?.forecastData
            ?.dailyForecastDataList
            ?.map { it.toDailyWeatherItem() }
            .run { weatherRepository.addWeather(city, this) }
    }

}