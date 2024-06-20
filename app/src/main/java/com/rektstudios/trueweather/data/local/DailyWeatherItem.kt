package com.rektstudios.trueweather.data.local

import io.realm.RealmObject

open class DailyWeatherItem(
    var dateEpoch: Long? = null,
    var dateString: String? = null,
    var minTempC: Double? = null,
    var minTempF: Double? = null,
    var maxTempC: Double? = null,
    var maxTempF: Double? = null,
    var avgTempC: Double? = null,
    var avgTempF: Double? = null,
    var avgVisKm: Double? = null,
    var avgVisMiles: Double? = null,
    var maxWindKph: Double? = null,
    var maxWindMph: Double? = null,
    var avgHumidity: Int? = null,
    var conditionText: String? = null,
    var imageUrl: String? = null
) : RealmObject()