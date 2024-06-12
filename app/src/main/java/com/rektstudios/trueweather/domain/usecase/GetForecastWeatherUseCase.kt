package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataDay
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend fun getWeatherHour(city: String): Flow<List<WeatherHourItem>> {
        val current = getWeatherHourFromCache(city)
        current?.firstOrNull()?.let { if(it.isNotEmpty()) return current }
        getWeatherFromRemote(city)
        return getWeatherHourFromCache(city)?: emptyFlow()
    }

    suspend fun getWeatherDay(city: String): Flow<List<WeatherDayItem>> {
        val current = getWeatherDayFromCache(city)
        current?.firstOrNull()?.let { if(it.isNotEmpty() && it.size>6) return current }
        getWeatherFromRemote(city)
        return getWeatherDayFromCache(city)?: emptyFlow()
    }

    private suspend fun getWeatherDayFromCache(city: String) = weatherRepository.getWeatherForecastInDaysFromCache(city)
    private suspend fun getWeatherHourFromCache(city: String) = weatherRepository.getWeatherForecastInHoursFromCache(city)
    private suspend fun getWeatherFromRemote(city: String) {
        val weather = weatherRepository.getForecastWeatherFromRemote(city).data
        weather?.current?.toWeatherDataHour()?.let {weatherRepository.addWeather(city,it)}
        weather?.forecast?.forecastDay?.forEach {day ->
            weatherRepository.addWeather(city,day.toWeatherDataDay())
            day.toWeatherDataHour().forEach {
                weatherRepository.addWeather(city,it)
            }
        }
    }
}