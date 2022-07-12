package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState

data class DashboardScreenState(
    val trackableId: String = "",
    val isAssetTrackerReady: Boolean = false,
    val trackableState: TrackableState? = null,
    val lastLocationUpdateInterval: Long? = null,
    val averageLocationUpdateInterval: Long? = null,
    val resolution: Resolution? = null,
    val trackableLocation: LocationUpdate? = null,
    val animatedLocation: LocationUpdate? = null,
    val showSubscriptionFailedDialog: Boolean = false
)
