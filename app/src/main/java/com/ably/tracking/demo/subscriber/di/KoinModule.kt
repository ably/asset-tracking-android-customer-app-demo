package com.ably.tracking.demo.subscriber.di

import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.demo.subscriber.data.ably.AssetTracker
import com.ably.tracking.demo.subscriber.data.ably.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.data.api.ApiDeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.data.api.buildDeliveryServiceApi
import com.ably.tracking.demo.subscriber.data.api.buildOkHttpClient
import com.ably.tracking.demo.subscriber.data.api.buildRetrofit
import com.ably.tracking.demo.subscriber.data.secrets.AndroidBase64Encoder
import com.ably.tracking.demo.subscriber.data.secrets.SharedPreferencesSecretsStorage
import com.ably.tracking.demo.subscriber.domain.DeliveryServiceDataSource
import com.ably.tracking.demo.subscriber.domain.orders.OrderManager
import com.ably.tracking.demo.subscriber.domain.secrets.Base64Encoder
import com.ably.tracking.demo.subscriber.domain.secrets.InMemorySecretsManager
import com.ably.tracking.demo.subscriber.domain.secrets.SecretsManager
import com.ably.tracking.demo.subscriber.domain.secrets.SecretsStorage
import com.ably.tracking.demo.subscriber.presentation.FusedLocationSource
import com.ably.tracking.demo.subscriber.presentation.navigation.Navigator
import com.ably.tracking.demo.subscriber.presentation.screen.createorder.CreateOrderViewModel
import com.ably.tracking.demo.subscriber.presentation.screen.dashboard.DashboardViewModel
import com.ably.tracking.demo.subscriber.presentation.screen.login.LoginViewModel
import com.ably.tracking.demo.subscriber.presentation.screen.orderarrived.OrderArrivedViewModel
import com.ably.tracking.ui.animation.CoreLocationAnimator
import com.ably.tracking.ui.animation.LocationAnimator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(), get()) }

    viewModel { DashboardViewModel(get(), get(), get(), get(), get()) }

    viewModel { CreateOrderViewModel(get(), get()) }

    viewModel { OrderArrivedViewModel(get()) }

    single { Navigator() }

    single { FusedLocationSource(get()) }

    single { AssetTracker(get()) }

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
