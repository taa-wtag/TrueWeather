package com.rektstudios.trueweather.repositories

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataDay
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.data.remote.reponses.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.other.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.other.Constants.getTimeTodayInLocale
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val realmDao: IRealmDao
) : IWeatherRepository {


    private suspend fun requestCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> {
        return try {
            val response = weatherApiService.getCurrentWeather(city = city)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    private suspend fun requestWeatherForecastFromRemote(
        city: String,
        days: Int
    ): Resource<ForecastWeatherResponse> {
        return try {
            val response = weatherApiService.getWeatherForecast(city = city, days = days)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getCurrentWeather(cityItem: CityItem): Flow<WeatherHourItem?> {
        var result : WeatherHourItem? = null
        realmDao.getCityWeatherCurrent(cityItem).collect {
            result = it
        }
        if (result!=null && (getTimeTodayInLocale(cityItem) - result!!.unixTime)< 9000L )
            return realmDao.getCityWeatherCurrent(cityItem)
        val response = requestCurrentWeatherFromRemote("${cityItem.cityName}, ${cityItem.country}")
        result = response.data?.current?.toWeatherDataHour()
        if (result != null) {
            realmDao.addWeather(
                cityItem,
                response.data?.current?.lastUpdated!!,
                listOf(result!!)
            )
        }
        return realmDao.getCityWeatherCurrent(cityItem)
    }

    override suspend fun getWeatherForecastInDays(cityItem: CityItem, days: Int): Flow<WeatherDayItem?> {
        val result = realmDao.getCityWeatherForecastInDays(cityItem).first()
        if (result.isValid)
            return realmDao.getCityWeatherForecastInDays(cityItem)
        val response = requestWeatherForecastFromRemote("${cityItem.cityName}, ${cityItem.country}", FORECAST_MAX_DAYS)
        response.data?.current?.let {
            realmDao.addWeather(cityItem,it.lastUpdated!!, listOf(it.toWeatherDataHour()))
        }
        response.data?.forecast?.forecastDay?.forEach {
            realmDao.addWeather(
                cityItem,
                it.date!!,
                it.toWeatherDataHour()
            )
            realmDao.addWeather(
                cityItem,
                it.date,
                it.toWeatherDataDay()
            )
        }
        return realmDao.getCityWeatherForecastInDays(cityItem)
    }

    override suspend fun getWeatherForecastInHours(cityItem: CityItem, days: Int): Flow<WeatherHourItem?> {
        val result = realmDao.getCityWeatherForecastInHours(cityItem).first()
        if (result.isValid)
            return realmDao.getCityWeatherForecastInHours(cityItem)
        val response = requestWeatherForecastFromRemote("${cityItem.cityName}, ${cityItem.country}", FORECAST_MAX_DAYS)
        response.data?.current?.let {
            realmDao.addWeather(cityItem,it.lastUpdated!!, listOf(it.toWeatherDataHour()))
        }
        response.data?.forecast?.forecastDay?.forEach {
            realmDao.addWeather(
                cityItem,
                it.date!!,
                it.toWeatherDataHour()
            )
            realmDao.addWeather(
                cityItem,
                it.date,
                it.toWeatherDataDay()
            )
        }
        return realmDao.getCityWeatherForecastInHours(cityItem)
    }

}