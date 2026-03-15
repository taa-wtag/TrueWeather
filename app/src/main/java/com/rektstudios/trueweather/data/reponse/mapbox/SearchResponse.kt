package com.rektstudios.trueweather.data.reponse.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("suggestions")
    val citySuggestions: List<CitySuggestion>?,
)
