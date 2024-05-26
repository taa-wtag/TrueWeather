package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("country_code_alpha_3")
    val countryCodeAlpha3: String?,
    val id: String?,
    val name: String?
)