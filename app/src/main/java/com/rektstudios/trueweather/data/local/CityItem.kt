package com.rektstudios.trueweather.data.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class CityItem: RealmObject() {
    @PrimaryKey
    var cityName: String = ""
    var weatherEveryDay: RealmList<WeatherDayItem> = RealmList()
    var weatherEveryHour: RealmList<WeatherHourItem> = RealmList()
}