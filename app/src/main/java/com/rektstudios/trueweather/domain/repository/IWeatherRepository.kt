package com.rektstudios.trueweather.domain.repository

import com.rektstudios.trueweather.data.local.CityItem
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
    suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?>
    suspend fun getWeatherForecastInDaysFromCache(cityItem: CityItem, days: Int = FORECAST_MAX_DAYS): Flow<WeatherDayItem>?
    suspend fun getWeatherForecastInHoursFromCache(cityItem: CityItem, days: Int = FORECAST_MAX_DAYS): Flow<WeatherHourItem>?
    suspend fun <T> addWeather(cityItem: CityItem, weather: T)
    suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse>
    suspend fun searchCity(city: String): Resource<PlaceResponse>
}