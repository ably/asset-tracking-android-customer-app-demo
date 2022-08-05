package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.demo.subscriber.domain.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.OrderManager
import com.ably.tracking.demo.subscriber.ui.screen.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TrackableIdViewModel @Inject constructor(
    private val orderManager: OrderManager,
    private val navigator: Navigator
) : ViewModel() {

    val state: MutableStateFlow<TrackableIdScreenState> = MutableStateFlow(TrackableIdScreenState())

    fun onTrackableIdChanged(value: String) = viewModelScope.launch {
        state.emit(
            state.value.copy(
                trackableId = value,
                isConfirmButtonEnabled = value.isNotBlank()
            )
        )
    }

    fun onClick() = viewModelScope.launch {
        val from = GeoCoordinates(51.1065859, 17.0312766)
        val to = GeoCoordinates(51.1114666, 17.025932)
        orderManager.createOrder(from, to)
        navigator.navigateToDashboard()
    }
}
