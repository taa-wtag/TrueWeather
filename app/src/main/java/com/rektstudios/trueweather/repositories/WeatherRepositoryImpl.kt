package com.rektstudios.trueweather.repositories

import android.location.Location
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.data.remote.reponses.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.PlaceResponse
import com.rektstudios.trueweather.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val realmDao: IRealmDao
) : IWeatherRepository {


    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> {
        return try {
            val response = weatherApiService.getCurrentWeather(city = city)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getForecastWeatherFromRemote(city: String, days: Int): Resource<ForecastWeatherResponse> {
        return try {
            val response = weatherApiService.getWeatherForecast(city = city, days = days)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?> {
        return realmDao.getCityWeatherCurrent(cityItem)
    }

    override suspend fun getWeatherForecastInDaysFromCache(cityItem: CityItem): Flow<WeatherDayItem>? {
        return realmDao.getCityWeatherForecastInDays(cityItem)
    }

    override suspend fun getWeatherForecastInHoursFromCache(cityItem: CityItem): Flow<WeatherHourItem>? {
        return realmDao.getCityWeatherForecastInHours(cityItem)
    }

    override suspend fun getCityNameFromRemote(location: Location): Resource<PlaceResponse> {
        return try {
            val response = weatherApiService.getCityName(latLon = "${location.latitude}, ${location.longitude}")
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun searchCity(city: String): Resource<PlaceResponse> {
        return try {
            val response = weatherApiService.searchCity(city = city)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

}