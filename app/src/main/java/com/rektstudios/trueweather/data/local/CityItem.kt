package com.rektstudios.trueweather.data.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CityItem(
    @PrimaryKey var cityName: String? = null,
    var backgroundColor: Int? = null,
    var weatherEveryDay: RealmList<DailyWeatherItem> = RealmList(),
    var weatherEveryHour: RealmList<HourlyWeatherItem> = RealmList()
) : RealmObject()