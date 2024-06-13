package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.domain.util.Constants.FORECAST_MIN_TIME_PAST
import com.rektstudios.trueweather.domain.util.Constants.MAX_BACKGROUND_COUNT
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Calendar
import java.util.Random
import javax.inject.Inject

class RealmDaoImpl @Inject constructor(
    private val realm: Realm
):IRealmDao {
    override suspend fun addCity(city: String)  {
        realm.executeTransactionAwait {
            try {
                it.copyToRealm(CityItem(city).apply {
                    backgroundColor = Random().nextInt(MAX_BACKGROUND_COUNT-1)+1
                })
            }catch (_: Exception){}

        }
    }

    override suspend fun deleteCity(city: String) {
        realm.executeTransactionAwait {
            getCityAsQuery(it, city).findFirst()?.deleteFromRealm()
        }
    }

    override suspend fun <T> addWeather(city: String, weather: T) {
            realm.executeTransactionAwait {
                getCityAsQuery(it,city).findFirst()?.let {cityItem ->
                    when (weather) {
                        is HourlyWeatherItem -> {
                                cityItem.weatherEveryHour
                                    .where()
                                    ?.equalTo("timeEpoch", weather.timeEpoch)
                                    ?.or()
                                    ?.lessThan("timeEpoch", Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                    ?.findAll()
                                    ?.deleteAllFromRealm()
                            if(weather.timeEpoch!!>Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                cityItem.weatherEveryHour.add(it.copyToRealm(weather))
                        }

                        is DailyWeatherItem -> {
                                cityItem.weatherEveryDay
                                    .where()
                                    ?.equalTo("dateEpoch", weather.dateEpoch)
                                    ?.or()
                                    ?.lessThan("dateEpoch", Calendar.getInstance().timeInMillis/1000L-FORECAST_MIN_TIME_PAST)
                                    ?.findAll()
                                    ?.deleteAllFromRealm()
                            if(weather.dateEpoch!!>Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                cityItem.weatherEveryDay.add(it.copyToRealm(weather))
                        }
                        is List<*> -> when{
                            weather.isListOf<HourlyWeatherItem>() -> {
                                weather.forEach {weatherHour ->
                                    if(weatherHour is HourlyWeatherItem){
                                    cityItem.weatherEveryHour
                                        .where()
                                        ?.equalTo("timeEpoch", weatherHour.timeEpoch)
                                        ?.or()
                                        ?.lessThan("timeEpoch", Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                        ?.findAll()
                                        ?.deleteAllFromRealm()
                                    if(weatherHour.timeEpoch!!>Calendar.getInstance().timeInMillis/1000L- FORECAST_MIN_TIME_PAST)
                                        cityItem.weatherEveryHour.add(it.copyToRealm(weatherHour))
                                    }
                                }
                            }
                            weather.isListOf<DailyWeatherItem>() -> {
                                weather.forEach { weatherDay ->
                                    if (weatherDay is DailyWeatherItem) {
                                        cityItem.weatherEveryDay
                                            .where()
                                            ?.equalTo("dateEpoch", weatherDay.dateEpoch)
                                            ?.or()
                                            ?.lessThan(
                                                "dateEpoch",
                                                Calendar.getInstance().timeInMillis / 1000L - FORECAST_MIN_TIME_PAST
                                            )
                                            ?.findAll()
                                            ?.deleteAllFromRealm()
                                        if (weatherDay.dateEpoch!! > Calendar.getInstance().timeInMillis / 1000L - FORECAST_MIN_TIME_PAST)
                                            cityItem.weatherEveryDay.add(it.copyToRealm(weatherDay))
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    inline fun <reified T> List<*>.isListOf(): Boolean {
        return all { it is T }
    }

    override fun getCityList(): Flow<List<CityItem>> = callbackFlow {
        val cityList = realm.where(CityItem::class.java).findAllAsync()
        val cityListListener = RealmChangeListener<RealmResults<CityItem>> {
            if(it.isValid) trySend(realm.copyFromRealm(it))
        }
        cityList.addChangeListener(cityListListener)
        awaitClose {
            cityList.removeChangeListener(cityListListener)
        }
    }


    override fun getCity(city: String): CityItem? = getCityAsQuery(realm,city).findFirst()

    private fun getCityAsQuery(iRealm: Realm, cityName: String = ""): RealmQuery<CityItem> = iRealm.where(CityItem::class.java).equalTo("cityName", cityName)


    override fun getCityWeatherCurrent(city: String): Flow<HourlyWeatherItem> =
    callbackFlow {
        val cityItem = getCityAsQuery(realm,city).findFirstAsync()
        val currentWeatherItemListener = RealmChangeListener<HourlyWeatherItem> {
            if(it.isValid) trySend(realm.copyFromRealm(it))
        }
        var currentWeatherItem: HourlyWeatherItem? = null
        cityItem.addChangeListener(RealmChangeListener<CityItem>{
            if(it.isValid) {
                currentWeatherItem = cityItem
                    .weatherEveryHour
                    .where()
                    ?.lessThanOrEqualTo("timeEpoch", Calendar.getInstance().timeInMillis / 1000)
                    ?.sort("timeEpoch", Sort.DESCENDING)
                    ?.findFirstAsync()
                currentWeatherItem?.addChangeListener(currentWeatherItemListener)
            }
        })
        awaitClose {
            currentWeatherItem?.removeAllChangeListeners()
            cityItem.removeAllChangeListeners()
        }
    }

    override fun getCityWeatherForecastInDays(city: String): Flow<List<DailyWeatherItem>> =
    callbackFlow {
        val cityItem = getCityAsQuery(realm,city).findFirstAsync()
        val dailyWeatherItemListener = RealmChangeListener<RealmResults<DailyWeatherItem>>{
            if(it.isValid) trySend(realm.copyFromRealm(it))
        }
        var dailyWeatherItemList: RealmResults<DailyWeatherItem>? = null
        cityItem.addChangeListener(RealmChangeListener<CityItem> {
            if(it.isValid) {
                dailyWeatherItemList = cityItem
                    ?.weatherEveryDay
                    ?.where()
                    ?.greaterThan("dateEpoch", Calendar.getInstance().timeInMillis / 1000)
                    ?.sort("dateEpoch")
                    ?.findAllAsync()
                dailyWeatherItemList?.addChangeListener(dailyWeatherItemListener)
            }
        })
        awaitClose {
            dailyWeatherItemList?.removeAllChangeListeners()
            cityItem.removeAllChangeListeners()
        }
    }
    override fun getCityWeatherForecastInHours(city: String): Flow<List<HourlyWeatherItem>> =
    callbackFlow {
        val cityItem = getCityAsQuery(realm,city).findFirstAsync()
        val hourlyWeatherItemListener = RealmChangeListener<RealmResults<HourlyWeatherItem>>{
            if(it.isValid) trySend(realm.copyFromRealm(it))
        }
        var hourlyWeatherItemList: RealmResults<HourlyWeatherItem>? = null
        cityItem.addChangeListener(RealmChangeListener<CityItem> {
            if(it.isValid) {
                hourlyWeatherItemList = cityItem
                    ?.weatherEveryHour
                    ?.where()
                    ?.greaterThan("timeEpoch", (Calendar.getInstance().timeInMillis / 1000))
                    ?.and()
                    ?.lessThan(
                        "timeEpoch",
                        (Calendar.getInstance().timeInMillis / 1000) + 86400
                    )
                    ?.sort("timeEpoch")
                    ?.findAllAsync()
                hourlyWeatherItemList?.addChangeListener(hourlyWeatherItemListener)
            }
        })
        awaitClose {
            hourlyWeatherItemList?.removeAllChangeListeners()
            cityItem.removeAllChangeListeners()
        }
    }

}