package com.ably.tracking.demo.subscriber.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

fun buildOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun buildRetrofit(
    okHttpClient: OkHttpClient,
    firebaseRegion: String,
    firebaseProjectName: String
): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://$firebaseRegion-$firebaseProjectName.cloudfunctions.net/deliveryService/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun buildDeliveryServiceApi(retrofit: Retrofit) = retrofit.create<DeliveryServiceApi>()
