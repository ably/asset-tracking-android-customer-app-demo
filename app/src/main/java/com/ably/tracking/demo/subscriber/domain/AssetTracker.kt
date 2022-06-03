package com.ably.tracking.demo.subscriber.domain

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

    // FIXME: Update with real tracking ID
    suspend fun startTracking() {
        subscriber = Subscriber.subscribers()
            .connection(connectionConfiguration)
            .logHandler(SubscriberLogHandler)
            .trackingId("1234")
            .start()
    }

    fun observeTrackableState(): Flow<TrackableState> = subscriber.trackableStates

    fun observeTrackableLocation(): Flow<LocationUpdate> = subscriber.locations

    fun observeResolution(): Flow<Resolution> = subscriber.resolutions
}
