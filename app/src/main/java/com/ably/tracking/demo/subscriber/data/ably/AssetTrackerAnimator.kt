package com.ably.tracking.demo.subscriber.data.ably

import com.ably.tracking.LocationUpdate
import com.ably.tracking.ui.animation.LocationAnimator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class AssetTrackerAnimator(private val locationAnimator: LocationAnimator) {
    fun update(
        locationUpdate: LocationUpdate,
        expectedIntervalBetweenLocationUpdatesInMilliseconds: Long,
    ) = locationAnimator.animateLocationUpdate(
        locationUpdate,
        expectedIntervalBetweenLocationUpdatesInMilliseconds
    )

    fun observeAnimatedTrackableLocation(): Flow<AssetTrackerAnimatorPosition> =
        locationAnimator.positionsFlow.combine(locationAnimator.cameraPositionsFlow) { location, camera ->
            AssetTrackerAnimatorPosition(location, camera)
        }
}
