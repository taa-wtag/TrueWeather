package com.rektstudios.trueweather.data.local

import kotlinx.coroutines.flow.Flow

interface IRealmDao {
    suspend fun addCity(cityItem: CityItem)
    suspend fun deleteCity(cityItem: CityItem)
    suspend fun addWeather(cityItem: CityItem, date: String, weatherHourItems: List<WeatherHourItem>)
    suspend fun addWeather(cityItem: CityItem, date: String, weatherDayItem: WeatherDayItem)
    suspend fun getCityList(): Flow<CityItem>
    suspend fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?>
    suspend fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>
    suspend fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>

}