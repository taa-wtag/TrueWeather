package com.rektstudios.trueweather.data.local

import io.realm.RealmObject

open class WeatherHourItem (
    var timeEpoch : Long = -1,
    var time: String = "",
    var tempC: Double = -999.0,
    var tempF: Double = -999.0,
    var feelsLikeC: Double = -999.0,
    var feelsLikeF: Double = -999.0,
    var visKm: Double = -1.0,
    var visMiles: Double = -1.0,
    var windKph: Double= -1.0,
    var windMph: Double = -1.0,
    var humidity: Int = -1,
    var isDay: Int = -1,
    var conditionText: String = "",
    var imageUrl: String=""
): RealmObject()