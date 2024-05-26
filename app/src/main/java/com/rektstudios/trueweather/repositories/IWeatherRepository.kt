package com.rektstudios.trueweather.repositories

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {

    suspend fun getCurrentWeather(cityItem: CityItem): Flow<WeatherHourItem?>
    suspend fun getWeatherForecastInDays(cityItem: CityItem, days: Int): Flow<WeatherDayItem?>
    suspend fun getWeatherForecastInHours(cityItem: CityItem, days: Int): Flow<WeatherHourItem?>

}