package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName

data class Suggestion(
    val context: Context?,
    val language: String?,
    val name: String?,
    @SerializedName("place_formatted")
    val placeFormatted: String?,
)