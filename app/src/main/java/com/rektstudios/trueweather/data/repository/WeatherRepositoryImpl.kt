package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.dao.IDatabaseDao
import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.Weather
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.data.reponse.weather.CityData
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.CheckResponseUtil
import com.rektstudios.trueweather.domain.util.Constants.SERVER_ERROR_MESSAGE
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val weatherApiService: WeatherApiService,
        private val realmDao: IDatabaseDao,
    ) : IWeatherRepository {
        override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> =
            withContext(Dispatchers.IO) {
                try {
                    CheckResponseUtil(weatherApiService.getCurrentWeather(city = city)).checkResponse()
                } catch (_: Exception) {
                    Resource.Error(SERVER_ERROR_MESSAGE, null)
                }
            }

        override suspend fun getForecastWeatherFromRemote(
            city: String,
            days: Int,
        ): Resource<ForecastWeatherResponse> =
            withContext(Dispatchers.IO) {
                try {
                    CheckResponseUtil(
                        weatherApiService.getWeatherForecast(
                            city = city,
                            days = days,
                        ),
                    ).checkResponse()
                } catch (_: Exception) {
                    Resource.Error(SERVER_ERROR_MESSAGE, null)
                }
            }

        override suspend fun getCurrentWeatherFromCache(city: String): Flow<HourlyWeatherEntity?> = realmDao.getCityWeatherCurrent(city)

        override suspend fun getWeatherForecastInDaysFromCache(
            city: String,
            days: Int,
        ): Flow<List<DailyWeatherEntity>> = realmDao.getCityWeatherForecastInDays(city)

        override suspend fun getWeatherForecastInHoursFromCache(
            city: String,
            days: Int,
        ): Flow<List<HourlyWeatherEntity>> = realmDao.getCityWeatherForecastInHours(city)

        override suspend fun <T : Weather> addWeather(
            city: String,
            weather: T,
        ) {
            when (weather) {
                is HourlyWeatherEntity -> realmDao.addWeather(weather)
                is DailyWeatherEntity -> realmDao.addWeather(weather)
            }
        }

        override suspend fun getCityNameFromRemote(
            lat: Double,
            lon: Double,
        ): Resource<List<CityData>> =
            withContext(Dispatchers.IO) {
                try {
                    CheckResponseUtil(weatherApiService.getCityName(latLon = "$lat, $lon")).checkResponse()
                } catch (_: Exception) {
                    Resource.Error(SERVER_ERROR_MESSAGE, null)
                }
            }

        override suspend fun searchCity(city: String): Resource<PlaceResponse> =
            withContext(Dispatchers.IO) {
                try {
                    CheckResponseUtil(weatherApiService.searchCity(city = city)).checkResponse()
                } catch (_: Exception) {
                    Resource.Error(SERVER_ERROR_MESSAGE, null)
                }
            }
    }
