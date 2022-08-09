package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import kotlinx.coroutines.flow.Flow

data class OrderData(
    val orderId: String,
    val orderState: Flow<TrackableState>,
    val orderLocation: Flow<LocationUpdate>,
    val resolution: Flow<Resolution>
)
