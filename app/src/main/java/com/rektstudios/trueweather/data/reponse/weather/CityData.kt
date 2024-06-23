package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class CityData(

    @SerializedName("country")
    val countryName: String?,

    @SerializedName("name")
    val cityName: String?,
)