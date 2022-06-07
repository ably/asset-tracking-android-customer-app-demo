package com.ably.tracking.demo.subscriber.di

import com.ably.tracking.demo.subscriber.domain.AssetTracker
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.ui.animation.CoreLocationAnimator
import com.ably.tracking.ui.animation.LocationAnimator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}