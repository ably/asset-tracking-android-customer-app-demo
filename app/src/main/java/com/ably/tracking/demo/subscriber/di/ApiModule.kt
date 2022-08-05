package com.ably.tracking.demo.subscriber.di

import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.demo.subscriber.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.DeliveryServiceApi
import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.buildDeliveryServiceApi
import com.ably.tracking.demo.subscriber.api.buildOkHttpClient
import com.ably.tracking.demo.subscriber.api.buildRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = buildOkHttpClient()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        buildRetrofit(okHttpClient, BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME)

    @Singleton
    @Provides
    fun provideDeliveryServiceApi(retrofit: Retrofit): DeliveryServiceApi =
        buildDeliveryServiceApi(retrofit)

    @Singleton
    @Provides
    fun provideDeliveryServiceDataSource(deliveryServiceApi: DeliveryServiceApi): DeliveryServiceDataSource =
        ApiDeliveryServiceDataSource(deliveryServiceApi)


}
