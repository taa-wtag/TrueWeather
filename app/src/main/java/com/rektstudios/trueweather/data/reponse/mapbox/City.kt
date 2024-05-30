package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("country_code_alpha_3")
    val countryCodeAlpha3: String?,
    val name: String?
)