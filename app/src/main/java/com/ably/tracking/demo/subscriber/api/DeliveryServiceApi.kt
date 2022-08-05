package com.ably.tracking.demo.subscriber.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DeliveryServiceApi {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    @POST("orders")
    suspend fun createOrder(
        @Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String,
        @Body body: CreateOrderBody
    ): CreateOrderResponse
}
