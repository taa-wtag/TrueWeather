package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val attribution: String?,
    @SerializedName("response_id")
    val responseId: String?,
    val suggestions: List<Suggestion>?
)