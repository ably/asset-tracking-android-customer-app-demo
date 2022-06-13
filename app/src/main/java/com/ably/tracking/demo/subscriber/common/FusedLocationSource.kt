package com.ably.tracking.demo.subscriber.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class FusedLocationSource @Inject constructor(
    @ActivityContext private val activity: Context
) :
    LocationSource {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var onLocationChangedListener: LocationSource.OnLocationChangedListener
    var lastRegisteredLocation: LatLng? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            lastRegisteredLocation = LatLng(
                result.lastLocation.latitude,
                result.lastLocation.longitude
            )
            onLocationChangedListener.onLocationChanged(result.lastLocation)
        }
    }

    @SuppressLint("MissingPermission")
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        if (!this::fusedLocationClient.isInitialized) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        }

        onLocationChangedListener = listener

        fusedLocationClient.requestLocationUpdates(
            LocationRequest.create(), locationCallback, Looper.getMainLooper()
        )
    }

    override fun deactivate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
