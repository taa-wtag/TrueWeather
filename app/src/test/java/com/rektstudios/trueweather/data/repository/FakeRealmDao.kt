package com.rektstudios.trueweather.data.repository

import androidx.lifecycle.MutableLiveData
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow

class FakeRealmDao: IRealmDao {
    private var cityList = mutableListOf<CityItem>()
    private var observableCityList = MutableLiveData(cityList)
    override suspend fun addCity(cityItem: CityItem) {
        if(cityList.find { it.cityName==cityItem.cityName } == null) {
            cityList.add(cityItem)
            observableCityList.postValue(cityList)
        }
    }

    override suspend fun deleteCity(cityItem: CityItem) {
        cityList.remove(cityItem)
        observableCityList.postValue(cityList)
    }

    override suspend fun <T> addWeather(cityItem: CityItem, weather: T) {
        when(weather){
            is WeatherHourItem -> cityList.firstOrNull { it.cityName == cityItem.cityName }?.weatherEveryHour?.add(weather)
            is WeatherDayItem -> cityList.firstOrNull { it.cityName == cityItem.cityName }?.weatherEveryDay?.add(weather)
        }
    }

    override fun getCityList(): Flow<CityItem> = observableCityList.value?.asFlow()?: emptyFlow()

    override fun getCity(city: String): Flow<CityItem?> = observableCityList.value?.find { it.cityName==city }.toFlow()

    override fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?>  =
        observableCityList
            .value
            ?.find { it.cityName == city.cityName }
            ?.weatherEveryHour
            ?.sortedByDescending { it.timeEpoch }
            ?.firstOrNull{it.timeEpoch<1717307500}
            .toFlow()

    override fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>?  =
        observableCityList
            .value
            ?.find { it.cityName == city.cityName }
            ?.weatherEveryDay
            ?.sortedByDescending { it.dateEpoch }
            ?.filter{it.dateEpoch>1717286399 }
            ?.asFlow()

    override fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>?   =
        observableCityList
            .value
            ?.find { it.cityName == city.cityName }
            ?.weatherEveryHour
            ?.sortedByDescending { it.timeEpoch }
            ?.filter{it.timeEpoch>1717307500}
            ?.asFlow()
}