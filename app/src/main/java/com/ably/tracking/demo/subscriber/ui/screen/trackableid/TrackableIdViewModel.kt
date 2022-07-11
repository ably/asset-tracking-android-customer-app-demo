package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TrackableIdViewModel @Inject constructor() : ViewModel() {

    val state: MutableStateFlow<TrackableIdScreenState> = MutableStateFlow(TrackableIdScreenState())

    fun onTrackableIdChanged(value: String) = viewModelScope.launch {
        state.emit(
            state.value.copy(
                trackableId = value,
                isConfirmButtonEnabled = value.isNotBlank()
            )
        )
    }
}
