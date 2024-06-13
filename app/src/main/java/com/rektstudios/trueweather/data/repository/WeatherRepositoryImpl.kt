package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.CheckResponseUtil
import com.rektstudios.trueweather.domain.util.Constants.SERVER_ERROR_MESSAGE
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val realmDao: IRealmDao
) : IWeatherRepository {


    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse>  = withContext(Dispatchers.IO){
        try {
            CheckResponseUtil(weatherApiService.getCurrentWeather(city = city)).checkResponse()
        } catch (e: Exception) {
            Resource.Error(SERVER_ERROR_MESSAGE, null)
        }
    }

    override suspend fun getForecastWeatherFromRemote(city: String, days: Int): Resource<ForecastWeatherResponse>  =
        withContext(Dispatchers.IO) {
            try { CheckResponseUtil(weatherApiService.getWeatherForecast(city = city, days = days)).checkResponse()
            } catch (e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}
        }

    override suspend fun getCurrentWeatherFromCache(city: String): Flow<HourlyWeatherItem> = realmDao.getCityWeatherCurrent(city)

    override suspend fun getWeatherForecastInDaysFromCache(city: String, days: Int): Flow<List<DailyWeatherItem>> = realmDao.getCityWeatherForecastInDays(city)

    override suspend fun getWeatherForecastInHoursFromCache(city: String, days: Int): Flow<List<HourlyWeatherItem>> = realmDao.getCityWeatherForecastInHours(city)
    override suspend fun <T> addWeather(city: String, weather: T) = realmDao.addWeather(city,weather)

    override suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse>  = withContext(Dispatchers.IO){
        try {
            CheckResponseUtil(weatherApiService.getCityName(latLon = "$lat, $lon")).checkResponse()
        } catch (e: Exception) { Resource.Error(SERVER_ERROR_MESSAGE, null)}
    }

    override suspend fun searchCity(city: String): Resource<PlaceResponse>  = withContext(Dispatchers.IO){
        try {
            CheckResponseUtil(weatherApiService.searchCity(city = city)).checkResponse()
        } catch (e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}
    }

}