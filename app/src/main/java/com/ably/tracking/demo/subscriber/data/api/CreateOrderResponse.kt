package com.ably.tracking.demo.subscriber.data.api

data class CreateOrderResponse(
    val orderId: Long,
    val ably: TokenResponse
)
