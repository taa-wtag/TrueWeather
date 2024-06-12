package com.rektstudios.trueweather.domain.location


interface ILocationTracker {
    suspend fun getCurrentLocation(): Pair<Double,Double>?
}