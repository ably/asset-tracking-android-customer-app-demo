package com.ably.tracking.demo.subscriber.data.api

import com.ably.tracking.demo.subscriber.domain.orders.GeoCoordinates

data class CreateOrderBody(
    val from: GeoCoordinates,
    val to: GeoCoordinates
)
