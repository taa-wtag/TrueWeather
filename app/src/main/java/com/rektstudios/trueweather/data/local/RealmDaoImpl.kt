package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.other.Constants.getDateEpochToday
import com.rektstudios.trueweather.other.Constants.getTimeEpochNow
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RealmDaoImpl @Inject constructor(
    private val realm: Realm
):IRealmDao {
    override suspend fun addCity(cityItem: CityItem)  {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                val city = getCityAsQuery(it, cityItem.cityName)
                if(city==null) it.copyToRealm(cityItem)
            }
        }
    }

    override suspend fun deleteCity(cityItem: CityItem) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                getCityAsQuery(it, cityItem.cityName)?.deleteFromRealm()
            }
        }
    }

    override suspend fun addWeather(cityItem: CityItem, weatherHourItems: List<WeatherHourItem>) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                val city = getCityAsQuery(it,cityItem.cityName)
                weatherHourItems.forEach {item ->
                    city?.weatherEveryHour
                        ?.where()
                        ?.equalTo("timeEpoch",item.timeEpoch)
                        ?.findAll()
                        ?.deleteAllFromRealm()
                }
                it.copyToRealm(weatherHourItems)
                city?.weatherEveryHour?.addAll(weatherHourItems)
            }
        }
    }

    override suspend fun addWeather(cityItem: CityItem, weatherDayItem: WeatherDayItem) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                val city = getCityAsQuery(it,cityItem.cityName)
                city?.weatherEveryDay
                    ?.where()
                    ?.equalTo("dateEpoch", weatherDayItem.dateEpoch)
                    ?.findAll()
                    ?.deleteAllFromRealm()
                it.copyToRealm(weatherDayItem)
                city?.weatherEveryDay?.add(weatherDayItem)
            }
        }
    }

    override fun getCityList(): Flow<CityItem> {
        return realm.where(CityItem::class.java)
            .findAll()
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    private fun getCityAsQuery(iRealm: Realm, cityName: String = ""): CityItem?{
        return iRealm.where(CityItem::class.java).equalTo("cityName", cityName).findFirst()
    }

    override fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?> {
        return getCityAsQuery(realm,city.cityName)
            ?.weatherEveryHour
            ?.where()
            ?.lessThan("timeEpoch", getTimeEpochNow())
            ?.sort("timeEpoch",Sort.DESCENDING)
            ?.findFirst()
            .toFlow()
    }

    override fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>? {
        return getCityAsQuery(realm,city.cityName)
            ?.weatherEveryDay
            ?.where()
            ?.greaterThanOrEqualTo("dateEpoch", getDateEpochToday())
            ?.sort("dateEpoch")
            ?.findAll()
            ?.asFlow()
            ?.flowOn(Dispatchers.IO)
    }

    override fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>? {
        return getCityAsQuery(realm,city.cityName)
            ?.weatherEveryHour
            ?.where()
            ?.greaterThanOrEqualTo("timeEpoch", getDateEpochToday())
            ?.sort("timeEpoch")
            ?.findAll()
            ?.asFlow()
            ?.flowOn(Dispatchers.IO)

    }

}