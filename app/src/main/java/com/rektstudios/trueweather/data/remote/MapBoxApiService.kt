package com.rektstudios.trueweather.data.remote

import com.rektstudios.trueweather.BuildConfig
import com.rektstudios.trueweather.data.reponse.mapbox.GeoJsonResponse
import com.rektstudios.trueweather.data.reponse.mapbox.SearchResponse
import com.rektstudios.trueweather.domain.util.Constants.SEARCH_LIMIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MapBoxApiService {
    @GET("search/searchbox/v1/suggest")
    suspend fun searchPlaceSuggestions(
        @Query("q") searchQuery: String,
        @Query("session_token") sessionToken: String?,
        @Query("language") language: String = "",
        @Query("limit") limit: Int = SEARCH_LIMIT,
        @Query("country") country: String = "",
        @Query("types") types: String = "place",
        @Query("access_token") accessToken: String = BuildConfig.TOKEN_KEY,
    ): Response<SearchResponse>

    @GET("search/searchbox/v1/suggest")
    suspend fun searchPlaceSuggestions(
        @QueryMap mapboxQuery: Map<String, String>,
    ): Response<SearchResponse>

    @GET("search/geocode/v6/reverse")
    suspend fun getPlaceSuggestions(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("session_token") sessionToken: String?,
        @Query("language") language: String = "",
        @Query("limit") limit: Int = 1,
        @Query("country") country: String = "",
        @Query("types") types: String = "place",
        @Query("access_token") accessToken: String = BuildConfig.TOKEN_KEY,
    ): Response<GeoJsonResponse>

    @GET("search/geocode/v6/reverse")
    suspend fun getPlaceSuggestions(
        @QueryMap mapboxQuery: Map<String, String>,
    ): Response<GeoJsonResponse>
}
