package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.EpochUtil.getTimeEpochNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend fun invoke(cityItem: CityItem): Flow<WeatherHourItem?> {
        val current = getWeatherFromCache(cityItem)
        current.firstOrNull()?.let {
            if (it.isValid && (it.timeEpoch - getTimeEpochNow()) < 900)
                return current
        }
        return getWeatherFromRemote(cityItem)
    }

    suspend fun invokeList(cityItem: CityItem): Pair<CityItem, Flow<WeatherHourItem?>> {
        return  Pair(cityItem,invoke(cityItem))
    }

    private suspend fun getWeatherFromCache(cityItem: CityItem) = weatherRepository.getCurrentWeatherFromCache(cityItem)
    private suspend fun getWeatherFromRemote(cityItem: CityItem): Flow<WeatherHourItem?> {
        weatherRepository.getCurrentWeatherFromRemote(cityItem.cityName).data?.current?.toWeatherDataHour()?.let {
            weatherRepository.addWeather(cityItem,it)
        }
        return getWeatherFromCache(cityItem)
    }
}