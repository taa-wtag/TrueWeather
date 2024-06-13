package com.rektstudios.trueweather.domain.usecase

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.domain.mapper.toHourlyWeatherItem
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {
    suspend operator fun invoke(city: String): Flow<HourlyWeatherItem> {
        val current = getWeatherFromCache(city)
        current.firstOrNull()?.let {
            if (it.isValid && (it.timeEpoch?.minus(Calendar.getInstance().timeInMillis/1000))?.let { it1-> it1 < FORECAST_MIN_TIME_PAST} != false)
                return current
        }
        return getWeatherFromRemote(city)
    }
    suspend operator fun invoke(cityList: List<CityItem>): Flow<List<HourlyWeatherItem>> {
        val hourlyWeatherItemFlowList = cityList.map {
            it.cityName?.let { it1 -> invoke(it1) }?: emptyFlow()
        }
        return combine(hourlyWeatherItemFlowList){it.toList()}
    }

    private suspend fun getWeatherFromCache(city: String) = weatherRepository.getCurrentWeatherFromCache(city)
    private suspend fun getWeatherFromRemote(city: String): Flow<HourlyWeatherItem> {
        weatherRepository.getCurrentWeatherFromRemote(city).data?.currentWeatherData?.toHourlyWeatherItem()?.let {
            weatherRepository.addWeather(city,it)
        }
        return getWeatherFromCache(city)
    }
}