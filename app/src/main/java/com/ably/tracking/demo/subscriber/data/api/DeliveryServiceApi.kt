package com.ably.tracking.demo.subscriber.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DeliveryServiceApi {

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }

    @GET("mapbox")
    suspend fun getMapboxToken(@Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String): TokenResponse

    @POST("orders")
    suspend fun createOrder(
        @Header(AUTHORIZATION_HEADER_NAME) authorizationHeader: String,
        @Body body: CreateOrderBody
    ): CreateOrderResponse
}
