package com.ably.tracking.demo.subscriber.domain

import android.util.Log
import com.ably.tracking.Accuracy
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.connection.Authentication
import com.ably.tracking.connection.ConnectionConfiguration
import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.subscriber.Subscriber
import kotlinx.coroutines.flow.Flow

class AssetTracker {

    private lateinit var subscriber: Subscriber

    private val connectionConfiguration: ConnectionConfiguration = ConnectionConfiguration(
        Authentication.basic(
            clientId = "AATSubscriberDemo",
            apiKey = BuildConfig.ABLY_API_KEY
        )
    )

    private val foregroundResolution: Resolution = Resolution(
        accuracy = Accuracy.MAXIMUM,
        desiredInterval = 500,
        minimumDisplacement = 1.0
    )

    private val backgroundResolution: Resolution = Resolution(
        accuracy = Accuracy.BALANCED,
        desiredInterval = 2000,
        minimumDisplacement = 10.0
    )

    suspend fun startTracking(trackableId: String) {
        Log.d("AssetTracker", "Starting subscriber")
        subscriber = Subscriber.subscribers()
            .connection(connectionConfiguration)
            .resolution(foregroundResolution)
            .logHandler(SubscriberLogHandler)
            .trackingId(trackableId)
            .start()
    }

    fun observeTrackableState(): Flow<TrackableState> = subscriber.trackableStates

    fun observeTrackableLocation(): Flow<LocationUpdate> = subscriber.locations

    fun observeResolution(): Flow<Resolution> = subscriber.resolutions

    suspend fun setForegroundResolution() {
        if (this::subscriber.isInitialized) {
            Log.d("AssetTracker", "Setting foreground resolution")
            subscriber.resolutionPreference(foregroundResolution)
        }
    }

    suspend fun setBackgroundResolution() {
        if (this::subscriber.isInitialized) {
            Log.d("AssetTracker", "Setting background resolution")
            subscriber.resolutionPreference(backgroundResolution)
        }
    }
}
