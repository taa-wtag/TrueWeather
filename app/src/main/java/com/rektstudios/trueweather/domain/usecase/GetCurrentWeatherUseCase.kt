package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.EpochUtil.getTimeEpochNow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend fun invoke(cityItem: CityItem): Flow<WeatherHourItem?> {
        val current = getWeatherFromCache(cityItem)
        current.first()?.let {
            if(it.isValid && (it.timeEpoch - getTimeEpochNow())<900 )
                return current
        } . run {
            return getWeatherFromRemote(cityItem)
        }
    }

    suspend fun invoke(cityList: List<CityItem>): List<Pair<CityItem,Flow<WeatherHourItem?>>> {
        return  cityList.map { Pair(it,invoke(it)) }
    }

    private suspend fun getWeatherFromCache(cityItem: CityItem) = weatherRepository.getCurrentWeatherFromCache(cityItem)
    private suspend fun getWeatherFromRemote(cityItem: CityItem): Flow<WeatherHourItem?> {
        weatherRepository.getCurrentWeatherFromRemote(cityItem.cityName).data?.current?.toWeatherDataHour()?.let {
            weatherRepository.addWeather(cityItem,it)
        }
        return getWeatherFromCache(cityItem)
    }
}