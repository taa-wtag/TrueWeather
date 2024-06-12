package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import com.rektstudios.trueweather.domain.util.Constants.MAX_BACKGROUND_COUNT
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Random
import javax.inject.Inject

class RealmDaoImpl @Inject constructor(
    private val realm: Realm
):IRealmDao {
    override suspend fun addCity(city: String)  {
        realm.executeTransaction {
            getCityAsQuery(it, city) ?: run{ it.copyToRealm(CityItem().apply {
                cityName = city
                backgroundColor = Random().nextInt(MAX_BACKGROUND_COUNT-1)
            }) }
        }
    }

    override suspend fun deleteCity(city: String) {
            realm.executeTransaction {
                getCityAsQuery(it, city)?.deleteFromRealm()
            }
    }

    override suspend fun <T> addWeather(city: String, weather: T) {
            realm.executeTransaction {
                getCityAsQuery(it,city)?.let {cityItem ->
                    when (weather) {
                        is WeatherHourItem -> {
                                cityItem.weatherEveryHour
                                    .where()
                                    ?.equalTo("timeEpoch", weather.timeEpoch)
                                    ?.or()
                                    ?.lessThan("timeEpoch", Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                    ?.findAll()
                                    ?.deleteAllFromRealm()
                            if(weather.timeEpoch>Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                cityItem.weatherEveryHour.add(it.copyToRealm(weather))
                        }

                        is WeatherDayItem -> {
                                cityItem.weatherEveryDay
                                    .where()
                                    ?.equalTo("dateEpoch", weather.dateEpoch)
                                    ?.or()
                                    ?.lessThan("dateEpoch", Calendar.getInstance().timeInMillis/1000L-FORECAST_MIN_TIME_PAST)
                                    ?.findAll()
                                    ?.deleteAllFromRealm()
                            if(weather.dateEpoch>Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                cityItem.weatherEveryDay.add(it.copyToRealm(weather))
                        }
                    }
                }
            }
    }

    override fun getCityList(): Flow<List<CityItem>> =realm.where(CityItem::class.java).findAll().toFlow()

    override fun getCity(city: String): CityItem? = getCityAsQuery(realm,city)

    private fun getCityAsQuery(iRealm: Realm, cityName: String = ""): CityItem? =
        iRealm.where(CityItem::class.java).equalTo("cityName", cityName).findFirst()


    override fun getCityWeatherCurrent(city: String): Flow<WeatherHourItem?> =
        getCityAsQuery(realm,city)
            ?.weatherEveryHour
            ?.where()
            ?.lessThanOrEqualTo("timeEpoch", Calendar.getInstance().timeInMillis/1000)
            ?.sort("timeEpoch",Sort.DESCENDING)
            ?.findFirst()
            .toFlow()

    override fun getCityWeatherForecastInDays(city: String): Flow<List<WeatherDayItem>>? =
        getCityAsQuery(realm,city)
            ?.weatherEveryDay
            ?.where()
            ?.greaterThan("dateEpoch", Calendar.getInstance().timeInMillis/1000)
            ?.sort("dateEpoch")
            ?.findAll()
            ?.toFlow()
    override fun getCityWeatherForecastInHours(city: String): Flow<List<WeatherHourItem>>? =
        getCityAsQuery(realm,city)
            ?.weatherEveryHour
            ?.where()
            ?.greaterThan("timeEpoch", (Calendar.getInstance().timeInMillis/1000))
            ?.and()
            ?.lessThan("timeEpoch", (Calendar.getInstance().timeInMillis/1000)+86400)
            ?.sort("timeEpoch")
            ?.findAll()
            ?.toFlow()

}