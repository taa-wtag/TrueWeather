package com.rektstudios.trueweather.domain.location

import android.location.Location

interface ILocationTracker {
    suspend fun getCurrentLocation(): Location?
}