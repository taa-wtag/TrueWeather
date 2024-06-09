package com.rektstudios.trueweather.data.local

import kotlinx.coroutines.flow.Flow

interface IRealmDao {
    suspend fun addCity(city: String)
    suspend fun deleteCity(city: String)
    suspend fun <T> addWeather(city: String, weather: T)
    fun getCityList(): Flow<List<CityItem>>
    fun getCity(city : String): CityItem?
    fun getCityWeatherCurrent(city: String): Flow<WeatherHourItem?>
    fun getCityWeatherForecastInDays(city: String): Flow<List<WeatherDayItem>>?
    fun getCityWeatherForecastInHours(city: String): Flow<List<WeatherHourItem>>?

}