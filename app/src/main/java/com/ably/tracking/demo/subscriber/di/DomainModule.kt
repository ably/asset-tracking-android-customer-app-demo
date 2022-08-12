package com.ably.tracking.demo.subscriber.di

import android.content.Context
import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.demo.subscriber.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.DeliveryServiceApi
import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.buildDeliveryServiceApi
import com.ably.tracking.demo.subscriber.api.buildOkHttpClient
import com.ably.tracking.demo.subscriber.api.buildRetrofit
import com.ably.tracking.demo.subscriber.domain.AssetTracker
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.domain.OrderManager
import com.ably.tracking.demo.subscriber.secrets.AndroidBase64Encoder
import com.ably.tracking.demo.subscriber.secrets.Base64Encoder
import com.ably.tracking.demo.subscriber.secrets.InMemorySecretsManager
import com.ably.tracking.demo.subscriber.secrets.SecretsManager
import com.ably.tracking.demo.subscriber.secrets.SecretsStorage
import com.ably.tracking.demo.subscriber.secrets.SharedPreferencesSecretsStorage
import com.ably.tracking.ui.animation.CoreLocationAnimator
import com.ably.tracking.ui.animation.LocationAnimator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideAssetTracker(): AssetTracker = AssetTracker()

    @Singleton
    @Provides
    fun provideAssetTrackerAnimator(locationAnimator: LocationAnimator): AssetTrackerAnimator =
        AssetTrackerAnimator(locationAnimator)

    @Singleton
    @Provides
    fun provideLocationAnimator(): LocationAnimator = CoreLocationAnimator()

    @Singleton
    @Provides
    fun provideOrderInteractor(
        deliveryServiceDataSource: DeliveryServiceDataSource,
        assetTracker: AssetTracker,
        secretsManager: SecretsManager
    ): OrderManager = OrderManager(
        deliveryServiceDataSource,
        assetTracker,
        secretsManager
    )

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

    @Singleton
    @Provides
    fun provideSecretsManager(
        deliveryServiceDataSource: DeliveryServiceDataSource,
        secretsStorage: SecretsStorage,
        base64Encoder: Base64Encoder
    ): SecretsManager =
        InMemorySecretsManager(deliveryServiceDataSource, secretsStorage, base64Encoder)

    @Singleton
    @Provides
    fun provideSecretsStorage(@ApplicationContext context: Context): SecretsStorage =
        SharedPreferencesSecretsStorage(context)

    @Singleton
    @Provides
    fun provideBase64Encoder(): Base64Encoder =
        AndroidBase64Encoder()
}
