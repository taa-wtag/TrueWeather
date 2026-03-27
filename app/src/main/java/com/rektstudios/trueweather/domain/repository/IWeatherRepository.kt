package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.Weather
import com.rektstudios.trueweather.data.reponse.weather.CityData
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MAX_DAYS_ALLOWED
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse>

    suspend fun getForecastWeatherFromRemote(
        city: String,
        days: Int = FORECAST_MAX_DAYS_ALLOWED,
    ): Resource<ForecastWeatherResponse>

    suspend fun getCurrentWeatherFromCache(city: String): Flow<HourlyWeatherEntity?>

    suspend fun getWeatherForecastInDaysFromCache(
        city: String,
        days: Int = FORECAST_MAX_DAYS,
    ): Flow<List<DailyWeatherEntity>>

    suspend fun getWeatherForecastInHoursFromCache(
        city: String,
        days: Int = FORECAST_MAX_DAYS,
    ): Flow<List<HourlyWeatherEntity>>

    suspend fun <T : Weather> addWeather(
        city: String,
        weather: T,
    )

    suspend fun getCityNameFromRemote(
        lat: Double,
        lon: Double,
    ): Resource<List<CityData>>

    suspend fun searchCity(city: String): Resource<PlaceResponse>
}
