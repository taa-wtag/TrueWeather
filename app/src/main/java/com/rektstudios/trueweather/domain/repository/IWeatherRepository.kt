package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse>
    suspend fun getForecastWeatherFromRemote(city: String, days: Int = FORECAST_MAX_DAYS): Resource<ForecastWeatherResponse>
    suspend fun getCurrentWeatherFromCache(city: String): Flow<WeatherHourItem?>
    suspend fun getWeatherForecastInDaysFromCache(city: String, days: Int = FORECAST_MAX_DAYS): Flow<List<WeatherDayItem>>?
    suspend fun getWeatherForecastInHoursFromCache(city: String, days: Int = FORECAST_MAX_DAYS): Flow<List<WeatherHourItem>>?
    suspend fun <T> addWeather(city: String, weather: T)
    suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse>
    suspend fun searchCity(city: String): Resource<PlaceResponse>
}