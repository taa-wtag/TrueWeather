package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.other.Constants.getDateTodayInLocale
import com.rektstudios.trueweather.other.Constants.getTimeTodayInLocale
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class RealmDaoImpl @Inject constructor(
    private val realm: Realm
):IRealmDao {
    override suspend fun addCity(cityItem: CityItem)  {
        withContext(Dispatchers.IO) {
            realm.copyToRealm(cityItem)
        }
    }

    override suspend fun deleteCity(cityItem: CityItem) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                it.where(CityItem::class.java)
                    .equalTo("name", cityItem.cityName)
                    .and()
                    .equalTo("countryCode", cityItem.countryCode)
                    .findFirst()
                    ?.deleteFromRealm()
            }
        }
    }

    override suspend fun addWeather(cityItem: CityItem, date: String, weatherHourItems: List<WeatherHourItem>) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction { iRealm ->
                val dateItem = queryDateItemHandler(iRealm, cityItem, date)
                weatherHourItems.forEach { it.dateItem = dateItem }
                iRealm.copyToRealm(weatherHourItems)
                dateItem.weatherEveryHour.addAll(weatherHourItems)
            }
        }
    }

    override suspend fun addWeather(cityItem: CityItem, date: String, weatherDayItem: WeatherDayItem) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction { iRealm ->
                val dateItem = queryDateItemHandler(iRealm, cityItem, date)
                weatherDayItem.dateItem = dateItem
                iRealm.copyToRealm(weatherDayItem)
                dateItem.weatherThisDay=weatherDayItem
            }
        }
    }

    override suspend fun getCityList(): Flow<CityItem> {
        return realm.where(CityItem::class.java).findAll().asFlow().flowOn(Dispatchers.IO)
    }

    override suspend fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?> {
        return realm.where(WeatherHourItem::class.java)
            .equalTo("dateItem.cityItem.id", city.id)
            .and()
            .lessThan("unixTime", getTimeTodayInLocale(city))
            .sort("unixTime",Sort.DESCENDING)
            .findFirst()
            .toFlow()
    }

    override suspend fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem> {
        return realm.where(WeatherDayItem::class.java)
            .equalTo("dateItem.cityItem.id", city.id)
            .and()
            .greaterThanOrEqualTo("unixTime", getDateTodayInLocale(city))
            .sort("unixTime")
            .findAll()
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem> {
        return realm.where(WeatherHourItem::class.java)
            .equalTo("dateItem.cityItem.id", city.id)
            .and()
            .greaterThanOrEqualTo("unixTime", getDateTodayInLocale(city))
            .sort("unixTime")
            .findAll()
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    private fun createDateItemFromStringAndLocale(date: String, cityItem: CityItem): DateItem{
        return DateItem().apply {
            this.cityItem = cityItem
            dateEpoch = SimpleDateFormat("yyyy-MM-dd", Locale(cityItem.language, cityItem.countryCode)).parse(date)!!.time
            dateText = date
        }
    }

    private fun queryDateItemHandler(iRealm: Realm, cityItem: CityItem, date: String): DateItem{
        return iRealm.where(DateItem::class.java)
            .equalTo("cityItem.id",cityItem.id)
            .and()
            .equalTo("dateText",date)
            .findFirst()?:iRealm.copyToRealm(createDateItemFromStringAndLocale(date,cityItem))

    }

}