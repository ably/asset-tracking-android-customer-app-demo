package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TrackableIdViewModel : ViewModel() {

    val state: MutableStateFlow<TrackableIdScreenState> = MutableStateFlow(TrackableIdScreenState())

    fun onTrackableIdChanged(value: String) = viewModelScope.launch {
        state.emit(
            state.value.copy(trackableId = value)
        )
    }
}
