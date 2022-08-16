package com.ably.tracking.demo.subscriber.di

import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.demo.subscriber.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.api.buildDeliveryServiceApi
import com.ably.tracking.demo.subscriber.api.buildOkHttpClient
import com.ably.tracking.demo.subscriber.api.buildRetrofit
import com.ably.tracking.demo.subscriber.common.FusedLocationSource
import com.ably.tracking.demo.subscriber.domain.AssetTracker
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.domain.OrderManager
import com.ably.tracking.demo.subscriber.secrets.AndroidBase64Encoder
import com.ably.tracking.demo.subscriber.secrets.Base64Encoder
import com.ably.tracking.demo.subscriber.secrets.InMemorySecretsManager
import com.ably.tracking.demo.subscriber.secrets.SecretsManager
import com.ably.tracking.demo.subscriber.secrets.SecretsStorage
import com.ably.tracking.demo.subscriber.secrets.SharedPreferencesSecretsStorage
import com.ably.tracking.demo.subscriber.ui.screen.Navigator
import com.ably.tracking.demo.subscriber.ui.screen.createorder.CreateOrderViewModel
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.DashboardViewModel
import com.ably.tracking.demo.subscriber.ui.screen.login.LoginViewModel
import com.ably.tracking.ui.animation.CoreLocationAnimator
import com.ably.tracking.ui.animation.LocationAnimator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(), get()) }

    viewModel { DashboardViewModel(get(), get(), get(), get()) }

    viewModel { CreateOrderViewModel(get(), get()) }

    single { Navigator() }

    single { FusedLocationSource(get()) }

    single { AssetTracker() }

    single { AssetTrackerAnimator(get()) }

    single<LocationAnimator> { CoreLocationAnimator() }

    single { OrderManager(get(), get(), get()) }

    single { buildOkHttpClient() }

    single { buildRetrofit(get(), BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME) }

    single { buildDeliveryServiceApi(get()) }

    single<DeliveryServiceDataSource> { ApiDeliveryServiceDataSource(get()) }

    single<SecretsManager> { InMemorySecretsManager(get(), get(), get()) }

    single<SecretsStorage> { SharedPreferencesSecretsStorage(get()) }

    single<Base64Encoder> { AndroidBase64Encoder() }
}
