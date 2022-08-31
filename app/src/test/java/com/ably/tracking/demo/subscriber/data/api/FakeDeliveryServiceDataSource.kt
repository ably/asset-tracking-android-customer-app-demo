package com.ably.tracking.demo.subscriber.data.api

import com.ably.tracking.demo.subscriber.domain.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.domain.orders.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.orders.Order
import kotlin.random.Random

class FakeDeliveryServiceDataSource : DeliveryServiceDataSource {

    var lastAuthorizationHeader: String? = null
        private set

    var mapboxToken: String = ""

    var ablyToken: String = ""

    override suspend fun getMapboxToken(authBase64: String): String {
        lastAuthorizationHeader = authBase64
        return mapboxToken
    }

    override suspend fun createOrder(
        authBase64: String,
        from: GeoCoordinates,
        to: GeoCoordinates
    ): Order {
        lastAuthorizationHeader = authBase64
        return Order(Random.nextInt().toString(), ablyToken)
    }
}
