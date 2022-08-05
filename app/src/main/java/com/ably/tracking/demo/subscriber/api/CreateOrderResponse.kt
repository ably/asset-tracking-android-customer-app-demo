package com.ably.tracking.demo.subscriber.api

data class CreateOrderResponse(
    val orderId: Long,
    val ably: TokenResponse
)
