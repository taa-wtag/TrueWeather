package com.rektstudios.trueweather.repositories

import android.location.Location
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.reponses.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.PlaceResponse
import com.rektstudios.trueweather.other.Constants.FORECAST_MAX_DAYS
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse>
    suspend fun getForecastWeatherFromRemote(city: String, days: Int = FORECAST_MAX_DAYS): Resource<ForecastWeatherResponse>
    suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?>
    suspend fun getWeatherForecastInDaysFromCache(cityItem: CityItem): Flow<WeatherDayItem>?
    suspend fun getWeatherForecastInHoursFromCache(cityItem: CityItem): Flow<WeatherHourItem>?
    suspend fun getCityNameFromRemote(location: Location): Resource<PlaceResponse>
    suspend fun searchCity(city: String): Resource<PlaceResponse>
}