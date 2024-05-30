package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.CheckResponseUtil
import com.rektstudios.trueweather.domain.util.Constants.SERVER_ERROR_MESSAGE
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val realmDao: IRealmDao
) : IWeatherRepository {


    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> = try {
            CheckResponseUtil(weatherApiService.getCurrentWeather(city = city)).checkResponse()
        } catch (e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}

    override suspend fun getForecastWeatherFromRemote(city: String, days: Int): Resource<ForecastWeatherResponse>  = try {
            CheckResponseUtil(weatherApiService.getWeatherForecast(city = city, days = days)).checkResponse()
        } catch (e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}

    override suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?> = realmDao.getCityWeatherCurrent(cityItem)

    override suspend fun getWeatherForecastInDaysFromCache(cityItem: CityItem, days: Int): Flow<WeatherDayItem>? = realmDao.getCityWeatherForecastInDays(cityItem)

    override suspend fun getWeatherForecastInHoursFromCache(cityItem: CityItem, days: Int): Flow<WeatherHourItem>? = realmDao.getCityWeatherForecastInHours(cityItem)
    override suspend fun <T> addWeather(cityItem: CityItem, weather: T) = realmDao.addWeather(cityItem,weather)

    override suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse> = try {
            CheckResponseUtil(weatherApiService.getCityName(latLon = "$lat, $lon")).checkResponse()
        } catch (e: Exception) { Resource.Error(SERVER_ERROR_MESSAGE, null)}

    override suspend fun searchCity(city: String): Resource<PlaceResponse> = try {
            CheckResponseUtil(weatherApiService.searchCity(city = city)).checkResponse()
        } catch (e: Exception) {Resource.Error(SERVER_ERROR_MESSAGE, null)}

}