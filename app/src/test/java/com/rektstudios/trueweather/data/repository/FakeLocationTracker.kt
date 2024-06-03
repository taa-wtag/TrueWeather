package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.domain.location.ILocationTracker

class FakeLocationTracker: ILocationTracker {
    var location = Pair(35.69, 139.69)
    override suspend fun getCurrentLocation(): Pair<Double, Double> {
        return location
    }

    fun setLocation(lat: Double, lon: Double){
        location = Pair(lat,lon)
    }

}