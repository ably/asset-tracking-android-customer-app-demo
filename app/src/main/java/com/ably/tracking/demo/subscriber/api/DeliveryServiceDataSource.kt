package com.ably.tracking.demo.subscriber.api

import com.ably.tracking.demo.subscriber.domain.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.Order

interface DeliveryServiceDataSource {
    suspend fun createOrder(
        authBase64: String,
        from: GeoCoordinates,
        to: GeoCoordinates
    ): Order
}
