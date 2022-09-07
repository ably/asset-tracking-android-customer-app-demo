package com.ably.tracking.demo.subscriber.presentation.screen.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.ConnectionException
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution
import com.ably.tracking.demo.subscriber.common.distanceTo
import com.ably.tracking.demo.subscriber.data.ably.AssetTrackerAnimator
import com.ably.tracking.demo.subscriber.data.ably.AssetTrackerAnimatorPosition
import com.ably.tracking.demo.subscriber.domain.orders.OrderManager
import com.ably.tracking.demo.subscriber.domain.orders.OrderState
import com.ably.tracking.demo.subscriber.presentation.FusedLocationSource
import com.ably.tracking.demo.subscriber.presentation.navigation.Navigator
import com.ably.tracking.demo.subscriber.presentation.screen.dashboard.map.DashboardScreenMapState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val orderId: String,
    private val navigator: Navigator,
    private val orderManager: OrderManager,
    private val assetTrackerAnimator: AssetTrackerAnimator,
    private val fusedLocationSource: FusedLocationSource
) : ViewModel() {

    companion object {
        private const val ROLLING_AVERAGE_INTERVAL_COUNT = 5
        private const val ORDER_ARRIVED_DISTANCE_IN_METERS = 50
    }

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
        val orderData = orderManager.startTrackingOrder(orderId)

        updateState {
            copy(
                orderId = orderData.orderId
            )
        }
        orderData.orderState
            .onEach(::onOrderStateChanged)
            .launchIn(this)
        orderData.orderLocation
            .onEach(::onOrderLocationChanged)
            .launchIn(this)
        orderData.resolution
            .onEach(::onResolutionChanged)
            .launchIn(this)
        assetTrackerAnimator.observeAnimatedTrackableLocation()
            .onEach(::onAnimatedOrderPositionChanged)
            .launchIn(this)
    }

    private suspend fun onOrderStateChanged(orderState: OrderState) {
        updateState {
            copy(orderState = orderState)
        }
    }

    private suspend fun onOrderLocationChanged(orderLocation: LocationUpdate) {
        val lastLocationUpdateTime = state.value.orderLocation?.location?.time
        val interval = lastLocationUpdateTime?.let { orderLocation.location.time - it }
        interval?.let {
            intervals.add(it)
        }
        val averageInterval = intervals.average()
        val lastRegisteredLocation = fusedLocationSource.lastRegisteredLocation
        val remainingDistance = orderLocation.location.distanceTo(lastRegisteredLocation)

        checkIfOrderArrived(remainingDistance)

        updateState {
            copy(
                orderLocation = orderLocation,
                remainingDistance = remainingDistance,
                lastLocationUpdateInterval = interval,
                averageLocationUpdateInterval = averageInterval
            )
        }

        state.value.resolution?.let { resolution ->
            assetTrackerAnimator.update(orderLocation, resolution.desiredInterval)
        }
    }

    private suspend fun checkIfOrderArrived(remainingDistance: Double?) {
        if (remainingDistance != null && remainingDistance < ORDER_ARRIVED_DISTANCE_IN_METERS) {
            orderManager.stopObserving()
            navigator.navigateToOrderArrived()
        }
    }

    private suspend fun onResolutionChanged(resolution: Resolution) {
        updateState {
            copy(resolution = resolution)
        }
        state.value.orderLocation?.let { location ->
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

    private suspend fun onAnimatedOrderPositionChanged(assetTrackerAnimatorPosition: AssetTrackerAnimatorPosition) {
        mapState.emit(
            mapState.value.copy(
                location = assetTrackerAnimatorPosition.location,
                cameraPosition = assetTrackerAnimatorPosition.camera
            )
        )
    }

    suspend fun onZoomedToOrderPosition() {
        mapState.emit(
            mapState.value.copy(
                isZoomedInToOrder = true
            )
        )
    }

    fun onSwitchMapModeButtonClicked() = viewModelScope.launch {
        mapState.value.let { currentMapState ->
            mapState.emit(
                currentMapState.copy(
                    shouldCameraFollowUserAndOrder = !currentMapState.shouldCameraFollowUserAndOrder
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
}
