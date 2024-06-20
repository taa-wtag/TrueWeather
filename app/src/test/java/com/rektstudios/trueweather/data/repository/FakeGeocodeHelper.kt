package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.domain.helper.IGeocodeHelper

class FakeGeocodeHelper : IGeocodeHelper {
    private val locationMap = mutableMapOf(
        Pair(Pair(23.72, 90.41), "Dhaka, Bangladesh"),
        Pair(Pair(26.68, 85.17), "Dhaka, India"),
        Pair(Pair(34.05, -118.24), "Los Angeles, United States"),
        Pair(Pair(13.75, 100.52), "Bangkok, Thailand"),
        Pair(Pair(35.69, 139.69), "Tokyo, Japan"),
        Pair(Pair(43.06, 141.35), "Sapporo, Japan"),
    )

    override fun geocodeLocation(lat: Double, lon: Double): String {
        return locationMap[Pair(lat, lon)] ?: ""
    }
}