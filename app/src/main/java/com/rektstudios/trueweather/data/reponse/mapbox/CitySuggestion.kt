package com.rektstudios.trueweather.data.reponse.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitySuggestion(
    @SerialName("context")
    val placeData: PlaceData?,
    @SerialName("name")
    val cityName: String?,
)
