package com.ably.tracking.demo.subscriber.domain

import com.ably.tracking.LocationUpdate
import com.ably.tracking.ui.animation.LocationAnimator
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class AssetTrackerAnimator @Inject constructor(
    private val locationAnimator: LocationAnimator
) {
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
