package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf

class FakeRealmDao: IRealmDao {
    private var cityList = mutableListOf<CityItem>()
    private var observableCityList = MutableSharedFlow<List<CityItem>>()
    override suspend fun addCity(city: String) {
        cityList.find { it.cityName==city }?:run {
            cityList.add(CityItem(city))
            observableCityList.emit(cityList)
        }
    }

    override suspend fun deleteCity(city: String) {
        cityList.firstOrNull { it.cityName == city }?.let {
            cityList.remove(it)
            observableCityList.emit(cityList)
        }
    }

    override suspend fun <T> addWeather(city: String, weather: T) {
        when(weather){
            is WeatherHourItem -> cityList.firstOrNull { it.cityName == city }?.weatherEveryHour?.add(weather)
            is WeatherDayItem -> cityList.firstOrNull { it.cityName == city }?.weatherEveryDay?.add(weather)
        }
        observableCityList.emit(cityList)
    }

    override fun getCityList(): Flow<List<CityItem>> = observableCityList

    override fun getCity(city: String): CityItem? = cityList.find { it.cityName==city }

    override fun getCityWeatherCurrent(city: String): Flow<WeatherHourItem?>  =
            cityList
                .find { it.cityName == city }
                ?.weatherEveryHour
                ?.sortedByDescending { it.timeEpoch }
                ?.firstOrNull { it.timeEpoch < 1717307500 }
                .toFlow()

    override fun getCityWeatherForecastInDays(city: String): Flow<List<WeatherDayItem>> =
        flowOf( cityList
            .find { it.cityName == city }
            ?.weatherEveryDay
            ?.toList()
            ?.filter { it.dateEpoch > 1717286399 }
            ?: emptyList()
        )
    override fun getCityWeatherForecastInHours(city: String): Flow<List<WeatherHourItem>> =
        flowOf( cityList
            .find { it.cityName == city }
            ?.weatherEveryHour?.toList()
            ?.filter { it.timeEpoch > 1717307500 }
            ?: emptyList()
        )
}