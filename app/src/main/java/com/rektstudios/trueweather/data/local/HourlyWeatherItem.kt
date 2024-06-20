package com.rektstudios.trueweather.data.local

import io.realm.RealmObject

open class HourlyWeatherItem(
    var timeEpoch: Long? = null,
    var timeString: String? = null,
    var tempC: Double? = null,
    var tempF: Double? = null,
    var feelsLikeC: Double? = null,
    var feelsLikeF: Double? = null,
    var visKm: Double? = null,
    var visMiles: Double? = null,
    var windKph: Double? = null,
    var windMph: Double? = null,
    var humidity: Int? = null,
    var isDay: Int? = null,
    var conditionText: String? = null,
    var imageUrl: String? = null
) : RealmObject()