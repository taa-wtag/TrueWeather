package com.rektstudios.trueweather.data.reponse.mapbox

import com.google.gson.annotations.SerializedName

data class PlaceData(
    @SerializedName("country")
    val countryData: CountryData?,
)