package com.rektstudios.trueweather.data.local

import kotlinx.coroutines.flow.Flow

interface IRealmDao {
    suspend fun addCity(cityItem: CityItem)
    suspend fun deleteCity(cityItem: CityItem)
    suspend fun addWeather(cityItem: CityItem, weatherHourItems: List<WeatherHourItem>)
    suspend fun addWeather(cityItem: CityItem, weatherDayItem: WeatherDayItem)
    fun getCityList(): Flow<CityItem>
    fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?>
    fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>?
    fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>?

}