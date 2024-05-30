package com.rektstudios.trueweather.data.repository

import android.location.Location
import com.rektstudios.trueweather.domain.location.ILocationTracker

class FakeLocationTracker: ILocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        TODO("Not yet implemented")
    }
}