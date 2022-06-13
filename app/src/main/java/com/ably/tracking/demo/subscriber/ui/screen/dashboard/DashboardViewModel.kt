package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.domain.AssetTracker
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimatorPosition
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.map.DashboardScreenMapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val assetTracker: AssetTracker,
    private val assetTrackerAnimator: AssetTrackerAnimator
) : ViewModel() {

    val state: MutableStateFlow<DashboardScreenState> = MutableStateFlow(DashboardScreenState())
    val mapState: MutableStateFlow<DashboardScreenMapState> =
        MutableStateFlow(DashboardScreenMapState())

    fun beginTracking(trackableId: String) = viewModelScope.launch {
        state.emit(state.value.copy(trackableId = trackableId, isAssetTrackerReady = false))
        assetTracker.startTracking(trackableId)
        state.emit(state.value.copy(isAssetTrackerReady = true))
        assetTracker.observeTrackableState()
            .onEach(::onTrackableStateChanged)
            .launchIn(this)
        assetTracker.observeTrackableLocation()
            .onEach(::onTrackableLocationChanged)
            .launchIn(this)
        assetTracker.observeResolution()
            .onEach(::onResolutionChanged)
            .launchIn(this)
        assetTrackerAnimator.observeAnimatedTrackableLocation()
            .onEach(::onAnimatedTrackablePositionChanged)
            .launchIn(this)
    }

    private suspend fun onTrackableStateChanged(trackableState: TrackableState) {
        state.emit(state.value.copy(trackableState = trackableState))
    }

    private suspend fun onTrackableLocationChanged(trackableLocation: LocationUpdate) {
        state.emit(state.value.copy(trackableLocation = trackableLocation))
        state.value.resolution?.let { resolution ->
            assetTrackerAnimator.update(trackableLocation, resolution.desiredInterval)
        }
    }

    private suspend fun onResolutionChanged(resolution: Resolution) {
        state.emit(state.value.copy(resolution = resolution))
        state.value.trackableLocation?.let { location ->
            assetTrackerAnimator.update(location, resolution.desiredInterval)
        }
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
        assetTracker.setForegroundResolution()
    }

    fun onEnterBackground() = viewModelScope.launch {
        assetTracker.setBackgroundResolution()
    }
}
