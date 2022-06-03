package com.ably.tracking.demo.subscriber.ui.screen

import com.ably.tracking.LocationUpdate
import com.ably.tracking.TrackableState
import com.google.android.gms.maps.model.LatLng

data class MainScreenState(
    val isAssetTrackerReady: Boolean = false,
    val trackableState: TrackableState? = null,
    val trackableLocation: LocationUpdate? = null
) {
    fun locationLatLng(): LatLng? = trackableLocation?.let {
        LatLng(it.location.latitude, it.location.longitude)
    }
}
