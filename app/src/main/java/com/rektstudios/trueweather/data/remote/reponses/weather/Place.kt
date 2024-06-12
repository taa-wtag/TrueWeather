package com.rektstudios.trueweather.data.remote.reponses.weather

data class Place(
    val country: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
)