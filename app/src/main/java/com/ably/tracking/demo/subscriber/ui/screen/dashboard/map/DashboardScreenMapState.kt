package com.ably.tracking.demo.subscriber.ui.screen.dashboard.map

import com.ably.tracking.ui.animation.Position
import com.google.android.gms.maps.model.LatLng

data class DashboardScreenMapState(
    val isZoomedInToTrackable: Boolean = false,
    val location: Position? = null,
    val cameraPosition: Position? = null
) {

    val isInInitialState = !isZoomedInToTrackable && location != null && cameraPosition != null

    fun locationLatLng(): LatLng? = location?.let {
        LatLng(it.latitude, it.longitude)
    }

    fun cameraLatLng(): LatLng? = cameraPosition?.let {
        LatLng(it.latitude, it.longitude)
    }
}
