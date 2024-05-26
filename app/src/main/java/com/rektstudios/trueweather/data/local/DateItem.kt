package com.rektstudios.trueweather.data.local

import io.realm.RealmList
import io.realm.RealmObject
import org.bson.types.ObjectId


open class DateItem: RealmObject() {
    var id: ObjectId = ObjectId()
    var dateEpoch: Long = -1
    var dateText: String = ""
    var cityItem: CityItem? = null
    var weatherThisDay: WeatherDayItem? = null
    var weatherEveryHour: RealmList<WeatherHourItem> = RealmList()
}