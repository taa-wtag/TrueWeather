package com.rektstudios.trueweather.data.remote

import com.rektstudios.trueweather.BuildConfig
import com.rektstudios.trueweather.domain.util.Constants.SEARCH_LIMIT

data class MapboxQuery(
    var searchQuery: String,
    var sessionToken: String? = null,
    var language: String? = null,
    var limit: Int = SEARCH_LIMIT,
    var country: String? = null,
    var types: String = "place",
    var accessToken: String = BuildConfig.TOKEN_KEY
)

fun MapboxQuery.toMap(): Map<String, String> {
    val queryMap = mutableMapOf<String, String>()
    queryMap["q"] = searchQuery
    sessionToken?.let { queryMap["session_token"] = it }
    language?.let { queryMap["language"] = it }
    queryMap["limit"] = limit.toString()
    country?.let { queryMap["country"] = it }
    queryMap["types"] = types
    queryMap["access_token"] = accessToken
    return queryMap
}