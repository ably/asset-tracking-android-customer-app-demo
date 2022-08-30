package com.ably.tracking.demo.subscriber.domain.orders

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import kotlinx.coroutines.flow.Flow

data class OrderData(
    val orderId: String,
    val orderState: Flow<OrderState>,
    val orderLocation: Flow<LocationUpdate>,
    val resolution: Flow<Resolution>
)
