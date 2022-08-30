package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.demo.subscriber.domain.orders.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.orders.Order

interface DeliveryServiceDataSource {
    suspend fun getMapboxToken(authBase64: String): String

    suspend fun createOrder(
        authBase64: String,
        from: GeoCoordinates,
        to: GeoCoordinates
    ): Order
}
