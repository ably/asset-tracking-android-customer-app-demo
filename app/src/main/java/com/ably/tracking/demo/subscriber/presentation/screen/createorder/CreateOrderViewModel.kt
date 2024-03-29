package com.ably.tracking.demo.subscriber.presentation.screen.createorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.demo.subscriber.domain.orders.GeoCoordinates
import com.ably.tracking.demo.subscriber.domain.orders.OrderManager
import com.ably.tracking.demo.subscriber.presentation.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateOrderViewModel(
    private val orderManager: OrderManager,
    private val navigator: Navigator
) : ViewModel() {

    val state: MutableStateFlow<CreateOrderScreenState> = MutableStateFlow(CreateOrderScreenState())

    fun onFromLatitudeChanged(value: String) = viewModelScope.launch {
        updateState {
            copy(fromLatitude = value)
        }
    }

    fun onFromLongitudeChanged(value: String) = viewModelScope.launch {
        updateState {
            copy(fromLongitude = value)
        }
    }

    fun onToLatitudeChanged(value: String) = viewModelScope.launch {
        updateState {
            copy(toLatitude = value)
        }
    }

    fun onToLongitudeChanged(value: String) = viewModelScope.launch {
        updateState {
            copy(toLongitude = value)
        }
    }

    private suspend fun updateState(update: CreateOrderScreenState.() -> CreateOrderScreenState) {
        state.emit(state.value.update())
    }

    fun onClick() = viewModelScope.launch {
        updateState {
            copy(showProgress = true)
        }
        val createOrderScreenState = state.value
        val orderId = orderManager.createOrder(
            createOrderScreenState.parseFromCoordinates(),
            createOrderScreenState.parseToCoordinates()
        )
        updateState {
            copy(showProgress = false)
        }
        navigator.navigateToDashboard(orderId)
    }

    private fun CreateOrderScreenState.parseFromCoordinates() =
        GeoCoordinates.fromStrings(fromLatitude, fromLongitude)

    private fun CreateOrderScreenState.parseToCoordinates() =
        GeoCoordinates.fromStrings(toLatitude, toLongitude)
}
