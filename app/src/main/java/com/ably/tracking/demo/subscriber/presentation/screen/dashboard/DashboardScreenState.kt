package com.ably.tracking.demo.subscriber.presentation.screen.dashboard

import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.demo.subscriber.domain.orders.OrderState

data class DashboardScreenState(
    val orderId: String = "",
    val orderState: OrderState? = null,
    val lastLocationUpdateInterval: Long? = null,
    val averageLocationUpdateInterval: Long? = null,
    val resolution: Resolution? = null,
    val remainingDistance: Double? = null,
    val orderLocation: LocationUpdate? = null,
    val animatedLocation: LocationUpdate? = null,
    val showSubscriptionFailedDialog: Boolean = false
)
