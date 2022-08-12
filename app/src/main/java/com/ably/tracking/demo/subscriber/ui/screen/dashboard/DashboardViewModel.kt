package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.ConnectionException
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.common.FixedSizeMutableList
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimatorPosition
import com.ably.tracking.demo.subscriber.domain.OrderManager
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMapState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val orderManager: OrderManager,
    private val assetTrackerAnimator: AssetTrackerAnimator
) : ViewModel() {

    val state: MutableStateFlow<DashboardScreenState> = MutableStateFlow(DashboardScreenState())
    val mapState: MutableStateFlow<DashboardScreenMapState> =
        MutableStateFlow(DashboardScreenMapState())

    private val intervals = FixedSizeMutableList(ROLLING_AVERAGE_INTERVAL_COUNT)

    fun onCreated() = viewModelScope.launch {
        try {
            beginTracking()
        } catch (connectionException: ConnectionException) {
            Log.d("AssetTracker", "Starting subscriber failed with $connectionException")
            connectionException.printStackTrace()
            updateState {
                copy(showSubscriptionFailedDialog = true)
            }
        }
    }

    private suspend fun CoroutineScope.beginTracking() {
        val orderData = orderManager.observeOrder()

        updateState {
            copy(
                trackableId = orderData.orderId
            )
        }
        orderData.orderState
            .onEach(::onTrackableStateChanged)
            .launchIn(this)
        orderData.orderLocation
            .onEach(::onTrackableLocationChanged)
            .launchIn(this)
        orderData.resolution
            .onEach(::onResolutionChanged)
            .launchIn(this)
        assetTrackerAnimator.observeAnimatedTrackableLocation()
            .onEach(::onAnimatedTrackablePositionChanged)
            .launchIn(this)
    }

    private suspend fun onTrackableStateChanged(trackableState: TrackableState) {
        updateState {
            copy(trackableState = trackableState)
        }
    }

    private suspend fun onTrackableLocationChanged(trackableLocation: LocationUpdate) {
        val lastLocationUpdateTime = state.value.trackableLocation?.location?.time
        val interval = lastLocationUpdateTime?.let { trackableLocation.location.time - it }
        interval?.let {
            intervals.add(it)
        }
        val averageInterval = intervals.average()

        updateState {
            copy(
                trackableLocation = trackableLocation,
                lastLocationUpdateInterval = interval,
                averageLocationUpdateInterval = averageInterval
            )
        }

        state.value.resolution?.let { resolution ->
            assetTrackerAnimator.update(trackableLocation, resolution.desiredInterval)
        }
    }

    private suspend fun onResolutionChanged(resolution: Resolution) {
        updateState {
            copy(resolution = resolution)
        }
        state.value.trackableLocation?.let { location ->
            assetTrackerAnimator.update(location, resolution.desiredInterval)
        }
    }

    fun onSubscriptionFailedDialogClose() = viewModelScope.launch {
        updateState {
            copy(showSubscriptionFailedDialog = false)
        }
    }

    private suspend fun updateState(update: DashboardScreenState.() -> DashboardScreenState) {
        state.emit(state.value.update())
    }

    private suspend fun onAnimatedTrackablePositionChanged(assetTrackerAnimatorPosition: AssetTrackerAnimatorPosition) {
        mapState.emit(
            mapState.value.copy(
                location = assetTrackerAnimatorPosition.location,
                cameraPosition = assetTrackerAnimatorPosition.camera
            )
        )
    }

    suspend fun onZoomedToTrackablePosition() {
        mapState.emit(
            mapState.value.copy(
                isZoomedInToTrackable = true
            )
        )
    }

    fun onSwitchMapModeButtonClicked() = viewModelScope.launch {
        mapState.value.let { currentMapState ->
            mapState.emit(
                currentMapState.copy(
                    shouldCameraFollowUserAndTrackable = !currentMapState.shouldCameraFollowUserAndTrackable
                )
            )
        }
    }

    fun onEnterForeground() = viewModelScope.launch {
        orderManager.setForegroundResolution()
    }

    fun onEnterBackground() = viewModelScope.launch {
        orderManager.setBackgroundResolution()
    }

    companion object {
        private const val ROLLING_AVERAGE_INTERVAL_COUNT = 5
    }
}
