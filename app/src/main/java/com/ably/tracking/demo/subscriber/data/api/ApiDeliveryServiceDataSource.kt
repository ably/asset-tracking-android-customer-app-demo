package com.ably.tracking.demo.subscriber.data.api

import com.ably.tracking.demo.subscriber.domain.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.domain.orders.GeoCoordinates

class ApiDeliveryServiceDataSource(private val deliveryServiceApi: DeliveryServiceApi) :
    DeliveryServiceDataSource {

    companion object {
        private const val AUTHORIZATION_HEADER_PREFIX = "Basic "
    }

    override suspend fun getMapboxToken(authBase64: String) =
        deliveryServiceApi.getMapboxToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token

    override suspend fun getAblyToken(authBase64: String): String =
        deliveryServiceApi.getAblyToken(AUTHORIZATION_HEADER_PREFIX + authBase64).token

    override suspend fun createOrder(authBase64: String, from: GeoCoordinates, to: GeoCoordinates) =
        deliveryServiceApi.createOrder(
            AUTHORIZATION_HEADER_PREFIX + authBase64,
            CreateOrderBody(from, to)
        ).orderId.toString()
}
