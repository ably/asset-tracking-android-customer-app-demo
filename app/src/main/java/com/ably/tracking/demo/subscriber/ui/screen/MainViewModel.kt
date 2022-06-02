package com.ably.tracking.demo.subscriber.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.LocationUpdate
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.domain.AssetTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val assetTracker: AssetTracker = AssetTracker()

    val state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())

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
    }

    private suspend fun onTrackableStateChanged(trackableState: TrackableState) {
        state.emit(state.value.copy(trackableState = trackableState))
    }

    private suspend fun onTrackableLocationChanged(trackableLocation: LocationUpdate) {
        state.emit(state.value.copy(trackableLocation = trackableLocation))
    }
}
