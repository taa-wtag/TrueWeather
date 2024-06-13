package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("name")
    val countryName: String?
)