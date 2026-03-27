package com.rektstudios.trueweather.data.remote

import com.rektstudios.trueweather.BuildConfig

data class MapboxReverseGeocode(
    var longitude: Double,
    var latitude: Double,
    var language: String? = null,
    var limit: Int = 1,
    var country: String? = null,
    var types: String = "place",
    var accessToken: String = BuildConfig.TOKEN_KEY,
)

fun MapboxReverseGeocode.toMap(): Map<String, String> {
    val queryMap = mutableMapOf<String, String>()
    queryMap["longitude"] = longitude.toString()
    queryMap["latitude"] = latitude.toString()
    language?.let { queryMap["language"] = it }
    queryMap["limit"] = limit.toString()
    country?.let { queryMap["country"] = it }
    queryMap["types"] = types
    queryMap["access_token"] = accessToken
    return queryMap
}
