package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.ably.AssetTracker
import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.secrets.SecretsManager
import kotlinx.coroutines.flow.map

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
            orderState = assetTracker.observeTrackableState().map { it.toOrderState() },
            orderLocation = assetTracker.observeTrackableLocation(),
            resolution = assetTracker.observeResolution()
        )

    private fun TrackableState.toOrderState(): OrderState =
        when (this) {
            is TrackableState.Online -> OrderState(
                OrderState.State.Online,
                null
            )
            is TrackableState.Publishing -> OrderState(
                OrderState.State.Publishing,
                null
            )
            is TrackableState.Failed -> OrderState(
                OrderState.State.Failed,
                errorInformation.message
            )
            is TrackableState.Offline -> OrderState(
                OrderState.State.Offline,
                errorInformation?.message
            )
        }

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
