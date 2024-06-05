package com.rektstudios.trueweather.data.repository

import android.location.Location
import android.location.LocationManager
import com.rektstudios.trueweather.domain.location.ILocationTracker

class FakeLocationTracker: ILocationTracker {
    private var location = Pair(34.05, -118.24)
    override suspend fun getCurrentLocation(): Pair<Double, Double> {
        return location
    }

    fun setLocation(lat: Double, lon: Double){
        location = Pair(lat,lon)
    }

}