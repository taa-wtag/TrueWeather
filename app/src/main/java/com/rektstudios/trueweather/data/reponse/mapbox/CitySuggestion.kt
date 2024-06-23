package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName

data class CitySuggestion(
    @SerializedName("context")
    val placeData: PlaceData?,
    @SerializedName("name")
    val cityName: String?,
)