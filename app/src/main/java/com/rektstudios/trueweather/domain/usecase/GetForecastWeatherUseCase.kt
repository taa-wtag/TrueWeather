package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataDay
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend fun invoke(cityItem: CityItem): Pair<Flow<WeatherDayItem>?, Flow<WeatherHourItem>?> {
        val current = getWeatherFromCache(cityItem)
        current.first?.first()?.let {
            if(it.isValid)
                return current
        }. run {
            return getWeatherFromRemote(cityItem)
        }
    }

    private suspend fun getWeatherFromCache(cityItem: CityItem) =
        Pair(
            weatherRepository.getWeatherForecastInDaysFromCache(cityItem),
            weatherRepository.getWeatherForecastInHoursFromCache(cityItem)
        )
    private suspend fun getWeatherFromRemote(cityItem: CityItem): Pair<Flow<WeatherDayItem>?, Flow<WeatherHourItem>?> {
        val weather = weatherRepository.getForecastWeatherFromRemote(cityItem.cityName).data
        weather?.current?.toWeatherDataHour()?.let {weatherRepository.addWeather(cityItem,it)}
        weather?.forecast?.forecastDay?.forEach {day ->
            weatherRepository.addWeather(cityItem,day.toWeatherDataDay())
            day.toWeatherDataHour().forEach {
                weatherRepository.addWeather(cityItem,it)
            }
        }
        return getWeatherFromCache(cityItem)
    }
}