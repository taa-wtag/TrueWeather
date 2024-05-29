package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class Suggestion(
    val context: Context?,
    val language: String?,
    val name: String?,
    @SerializedName("place_formatted")
    val placeFormatted: String?,
)