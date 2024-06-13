package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName


data class SearchResponse(

    @SerializedName("suggestions")
    val citySuggestions: List<CitySuggestion>?
)