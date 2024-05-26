package com.rektstudios.trueweather.data.local

import com.rektstudios.trueweather.other.Constants.WeatherCondition
import io.realm.RealmObject

open class WeatherDayItem: RealmObject() {
    var dateItem: DateItem? = null
    var minTempC: Double = -999.0
    var minTempF: Double = -999.0
    var maxTempC: Double = -999.0
    var maxTempF: Double = -999.0
    var avgTempC: Double = -999.0
    var avgTempF: Double = -999.0
    var avgVisKm: Double = -1.0
    var avgVisMiles: Double = -1.0
    var maxWindKph: Double= -1.0
    var maxWindMph: Double = -1.0
    var avgHumidity: Int = -1
    var conditionText: String = ""
}