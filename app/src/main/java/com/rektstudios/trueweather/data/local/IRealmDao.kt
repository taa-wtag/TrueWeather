package com.rektstudios.trueweather.data.local

import kotlinx.coroutines.flow.Flow

interface IRealmDao {
    suspend fun addCity(cityItem: CityItem)
    suspend fun deleteCity(cityItem: CityItem)
    suspend fun <T> addWeather(cityItem: CityItem, weather: T)
    fun getCityList(): Flow<CityItem>
    fun getCity(city : String): Flow<CityItem?>
    fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?>
    fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>?
    fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>?

}