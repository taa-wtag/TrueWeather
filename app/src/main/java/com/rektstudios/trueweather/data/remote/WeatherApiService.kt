package com.rektstudios.trueweather.data.remote

import com.rektstudios.trueweather.BuildConfig
import com.rektstudios.trueweather.data.remote.reponses.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.remote.reponses.weather.PlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") city: String
    ): Response<CurrentWeatherResponse>

    @GET("/forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") city: String,
        @Query("days") days: Int
    ): Response<ForecastWeatherResponse>

    @GET("/search.json")
    suspend fun getCityName(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") latLon: String,
    ): Response<PlaceResponse>

}