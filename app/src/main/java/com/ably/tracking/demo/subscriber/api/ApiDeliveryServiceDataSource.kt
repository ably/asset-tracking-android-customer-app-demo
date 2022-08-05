package com.ably.tracking.demo.subscriber.api

import com.ably.tracking.demo.subscriber.domain.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.Order

class ApiDeliveryServiceDataSource(private val deliveryServiceApi: DeliveryServiceApi) :
    DeliveryServiceDataSource {

    companion object {
        private const val AUTHORIZATION_HEADER_PREFIX = "Basic "
    }

    override suspend fun createOrder(authBase64: String, from: GeoCoordinates, to: GeoCoordinates) =
        deliveryServiceApi.createOrder(
            AUTHORIZATION_HEADER_PREFIX + authBase64,
            CreateOrderBody(from, to)
        ).toOrder()

    private fun CreateOrderResponse.toOrder() =
        Order(orderId.toString(), ably.token)
}
