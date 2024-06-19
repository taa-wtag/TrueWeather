package com.rektstudios.trueweather.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.rektstudios.trueweather.domain.location.ILocationTracker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): ILocationTracker {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Pair<Double, Double>? {

        return if(checkLocationPermissionsNotGranted()) null
        else suspendCancellableCoroutine {cancellableContinuation ->
            locationClient
                .lastLocation.apply {
                    if (isComplete) {
                        if (isSuccessful && result!=null) {
                            cancellableContinuation.resume(Pair(result.latitude, result.longitude))
                        } else {
                            cancellableContinuation.resume(null)
                        }
                        return@suspendCancellableCoroutine
                    }
                }
                .addOnSuccessListener {location: Location? ->
                    if (location!=null) cancellableContinuation.resume(Pair(location.latitude,location.longitude))
                    else cancellableContinuation.resume(null)
                }
                .addOnCanceledListener {
                    cancellableContinuation.cancel()
                }
                .addOnFailureListener {
                    cancellableContinuation.resume(null)
                }
        }
    }

    private fun checkLocationPermissionsNotGranted(): Boolean{
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return !hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled
    }
}