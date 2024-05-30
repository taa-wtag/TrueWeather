package com.rektstudios.trueweather.data.remote

import com.rektstudios.trueweather.BuildConfig
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.util.Constants.SEARCH_LIMIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapBoxApiService {
    @GET("/search/searchbox/v1/suggest")
    suspend fun searchPlacesSuggest(
        @Query("q") searchQuery: String,
        @Query("session_token") sessionToken: String?,
        @Query("language") language: String="",
        @Query("limit") limit: Int = SEARCH_LIMIT,
        @Query("proximity") proximity: String = "",
        @Query("country") country: String = "",
        @Query("types") types: String = "place",
        @Query("access_token") accessToken: String = BuildConfig.TOKEN_KEY
    ): Response<SearchResponse>
}