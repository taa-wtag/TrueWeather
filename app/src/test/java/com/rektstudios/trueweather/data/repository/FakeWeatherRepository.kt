package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository:IWeatherRepository {
    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getForecastWeatherFromRemote(
        city: String,
        days: Int
    ): Resource<ForecastWeatherResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?> {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherForecastInDaysFromCache(
        cityItem: CityItem,
        days: Int
    ): Flow<WeatherDayItem>? {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherForecastInHoursFromCache(
        cityItem: CityItem,
        days: Int
    ): Flow<WeatherHourItem>? {
        TODO("Not yet implemented")
    }

    override suspend fun <T> addWeather(cityItem: CityItem, weather: T) {
        TODO("Not yet implemented")
    }

    override suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCity(city: String): Resource<PlaceResponse> {
        TODO("Not yet implemented")
    }
}