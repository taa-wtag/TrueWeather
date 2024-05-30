package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.domain.util.EpochUtil.getDateEpochToday
import com.rektstudios.trueweather.domain.util.EpochUtil.getTimeEpochNow
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

    override suspend fun <T> addWeather(cityItem: CityItem, weather: T) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction {
                val city = getCityAsQuery(it,cityItem.cityName)
                when(weather){
                    is WeatherHourItem -> {
                        city?.weatherEveryHour
                            ?.where()
                            ?.equalTo("timeEpoch", weather.timeEpoch)
                            ?.findAll()
                            ?.deleteAllFromRealm()
                        it.copyToRealm(weather)
                        city?.weatherEveryHour?.add(weather)
                    }
                    is WeatherDayItem -> {
                        city?.weatherEveryDay
                            ?.where()
                            ?.equalTo("dateEpoch", weather.dateEpoch)
                            ?.findAll()
                            ?.deleteAllFromRealm()
                        it.copyToRealm(weather)
                        city?.weatherEveryDay?.add(weather)
                    }
                }
            }
        }
    }

    override fun getCityList(): Flow<CityItem> =
        realm.where(CityItem::class.java)
            .findAll()
            .asFlow()
            .flowOn(Dispatchers.IO)

    override fun getCity(city: String): Flow<CityItem?> = getCityAsQuery(realm,city).toFlow().flowOn(Dispatchers.IO)
    private fun getCityAsQuery(iRealm: Realm, cityName: String = ""): CityItem? =
        iRealm.where(CityItem::class.java).equalTo("cityName", cityName).findFirst()


    override fun getCityWeatherCurrent(city: CityItem): Flow<WeatherHourItem?> =
        getCityAsQuery(realm,city.cityName)
            ?.weatherEveryHour
            ?.where()
            ?.lessThan("timeEpoch", getTimeEpochNow())
            ?.sort("timeEpoch",Sort.DESCENDING)
            ?.findFirst()
            .toFlow()
            .flowOn(Dispatchers.IO)

    override fun getCityWeatherForecastInDays(city: CityItem): Flow<WeatherDayItem>? =
        getCityAsQuery(realm,city.cityName)
            ?.weatherEveryDay
            ?.where()
            ?.greaterThanOrEqualTo("dateEpoch", getDateEpochToday())
            ?.sort("dateEpoch")
            ?.findAll()
            ?.asFlow()
            ?.flowOn(Dispatchers.IO)

    override fun getCityWeatherForecastInHours(city: CityItem): Flow<WeatherHourItem>? =
        getCityAsQuery(realm,city.cityName)
            ?.weatherEveryHour
            ?.where()
            ?.greaterThanOrEqualTo("timeEpoch", getDateEpochToday())
            ?.sort("timeEpoch")
            ?.findAll()
            ?.asFlow()
            ?.flowOn(Dispatchers.IO)

}