package com.rektstudios.trueweather.data.local

import kotlinx.coroutines.flow.Flow

interface IRealmDao {

    suspend fun addCity(city: String)

    suspend fun deleteCity(city: String)

    suspend fun <T> addWeather(city: String, weather: T)

    fun getCityList(): Flow<List<CityItem>>

    suspend fun getCity(city: String): CityItem?

    fun getCityWeatherCurrent(city: String): Flow<HourlyWeatherItem?>

    fun getCityWeatherForecastInDays(city: String): Flow<List<DailyWeatherItem>>

    fun getCityWeatherForecastInHours(city: String): Flow<List<HourlyWeatherItem>>

}