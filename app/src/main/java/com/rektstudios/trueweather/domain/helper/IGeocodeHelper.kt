package com.rektstudios.trueweather.domain.helper

interface IGeocodeHelper {
    fun geocodeLocation(lat: Double, lon: Double): String
}