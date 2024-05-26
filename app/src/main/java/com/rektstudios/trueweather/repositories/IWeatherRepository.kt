package com.rektstudios.trueweather.repositories

import android.location.Location
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.reponses.weather.PlaceResponse
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getCurrentWeather(cityItem: CityItem): Flow<WeatherHourItem?>
    suspend fun getWeatherForecastInDays(cityItem: CityItem, days: Int): Flow<WeatherDayItem?>
    suspend fun getWeatherForecastInHours(cityItem: CityItem, days: Int): Flow<WeatherHourItem?>
    suspend fun getCityName(location: Location): Resource<PlaceResponse>

}