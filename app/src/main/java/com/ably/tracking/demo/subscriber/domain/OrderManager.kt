package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.secrets.SecretsManager

class OrderManager(
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val assetTracker: AssetTracker,
    private val secretsManager: SecretsManager
) {
    suspend fun createOrder(from: GeoCoordinates, to: GeoCoordinates) {
        val authenticationHeader = secretsManager.getAuthorizationHeader()!!
        val authUsername = secretsManager.getUsername()!!
        val (orderId, ablyToken) = deliveryServiceDataSource.createOrder(
            authenticationHeader,
            from,
            to
        )
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

    suspend fun stopObserving() {
        assetTracker.stopTracking()
    }
}
