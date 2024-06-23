package com.rektstudios.trueweather.data.helper

import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.rektstudios.trueweather.domain.helper.IGeocodeHelper
import java.util.Locale
import javax.inject.Inject

class GeocodeHelper @Inject constructor(private val context: Context) : IGeocodeHelper {

    override fun geocodeLocation(lat: Double, lon: Double): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            var cityName = ""
            Geocoder(context, Locale.getDefault()).getFromLocation(lat, lon, 1) {
                it?.let { cityName = it[0].locality + ", " + it[0].countryName }
            }
            cityName
        } else ""
    }

}