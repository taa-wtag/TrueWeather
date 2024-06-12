package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class Region(
    val id: String?,
    val name: String?,
    @SerializedName("region_code")
    val regionCode: String?,
    @SerializedName("region_code_full")
    val regionCodeFull: String?
)