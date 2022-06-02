package com.ably.tracking.demo.subscriber.ui.screen

import com.ably.tracking.LocationUpdate
import com.ably.tracking.TrackableState

data class MainScreenState(
    val isAssetTrackerReady: Boolean = false,
    val trackableState: TrackableState? = null,
    val trackableLocation: LocationUpdate? = null
)
