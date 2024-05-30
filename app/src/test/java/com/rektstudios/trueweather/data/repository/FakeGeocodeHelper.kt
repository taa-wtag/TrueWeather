package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.domain.helper.IGeocodeHelper

class FakeGeocodeHelper:IGeocodeHelper {
    override fun geocodeLocation(lat: Double, lon: Double): String {
        TODO("Not yet implemented")
    }
}