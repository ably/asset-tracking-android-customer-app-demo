package com.ably.tracking.demo.subscriber.ui.bottomsheet

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution

data class LocationUpdateBottomSheetData(
    val locationUpdate: LocationUpdate?,
    val resolution: Resolution?,
    val remainingDistance: Double?,
    val lastLocationUpdateInterval: Long?,
    val averageLocationUpdateInterval: Long?
)
