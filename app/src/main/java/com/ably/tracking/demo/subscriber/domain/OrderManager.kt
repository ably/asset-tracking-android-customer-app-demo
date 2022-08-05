package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import javax.inject.Inject

class OrderManager @Inject constructor(
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val assetTracker: AssetTracker,
    private val authBase64: String,
    private val authUsername: String
) {
    suspend fun createOrder(from: GeoCoordinates, to: GeoCoordinates) {
        val (orderId, ablyToken) = deliveryServiceDataSource.createOrder(authBase64, from, to)
        assetTracker.startTracking(orderId, ablyToken, authUsername)
    }

    fun observeOrder() =
        OrderData(
            orderId = assetTracker.trackableId,
            orderState = assetTracker.observeTrackableState(),
            orderLocation = assetTracker.observeTrackableLocation(),
            resolution = assetTracker.observeResolution()
        )

    suspend fun setForegroundResolution() {
        assetTracker.setForegroundResolution()
    }

    suspend fun setBackgroundResolution() {
        assetTracker.setBackgroundResolution()
    }
}
