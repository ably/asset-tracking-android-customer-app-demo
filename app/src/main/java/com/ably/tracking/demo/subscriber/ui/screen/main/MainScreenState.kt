package com.ably.tracking.demo.subscriber.ui.screen.main

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState

data class MainScreenState(
    val trackableId: String = "",
    val isAssetTrackerReady: Boolean = false,
    val trackableState: TrackableState? = null,
    val resolution: Resolution? = null,
    val trackableLocation: LocationUpdate? = null,
    val animatedLocation: LocationUpdate? = null
)
