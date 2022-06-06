package com.ably.tracking.demo.subscriber.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.domain.AssetTracker
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.domain.AssetTrackerAnimatorPosition
import com.ably.tracking.demo.subscriber.ui.screen.main.map.MainScreenMapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val assetTracker: AssetTracker = AssetTracker()
    private val assetTrackerAnimator: AssetTrackerAnimator = AssetTrackerAnimator()

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())
    val mapState: MutableStateFlow<MainScreenMapState> = MutableStateFlow(MainScreenMapState())

    fun beginTracking() = viewModelScope.launch {
        state.emit(state.value.copy(isAssetTrackerReady = false))
        assetTracker.startTracking()
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
}
