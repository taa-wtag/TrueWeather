package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_number")
    val addressNumber: String?,
    val name: String?,
    @SerializedName("street_name")
    val streetName: String?
)