package com.ably.tracking.demo.subscriber.api

import com.ably.tracking.demo.subscriber.domain.GeoCoordinates

data class CreateOrderBody(
    val from: GeoCoordinates,
    val to: GeoCoordinates
)
