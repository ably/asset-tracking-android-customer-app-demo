package com.ably.tracking.demo.subscriber.presentation.screen.dashboard.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ably.tracking.demo.subscriber.presentation.FusedLocationSource
import com.ably.tracking.demo.subscriber.presentation.screen.dashboard.DashboardViewModel
import com.ably.tracking.demo.subscriber.presentation.theme.AATSubscriberDemoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

const val longAnimationDuration = 1000
const val shortAnimationDuration = 100
const val mapZoomLevel = 14F
const val mapBoundsPadding = 64

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreenMap(
    viewModel: DashboardViewModel,
    locationPermissionState: PermissionState,
    locationSource: FusedLocationSource
) = AATSubscriberDemoTheme {
    val mapState = viewModel.mapState.collectAsState().value
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()

    /**
     * This is a hack that solves animation stutters
     * See https://github.com/ably/ably-asset-tracking-android/issues/681
     * and https://github.com/ably/ably-asset-tracking-android/issues/683
     */
    SideEffect {
        coroutineScope.launch {
            when {
                /**
                 * This handles the initial state of map, when it's not zoomed to anything,
                 * and not already zooming, it should zoom to the order location,
                 * before we start animating between the provided camera positions
                 */
                mapState.isInInitialState && !cameraPositionState.isMoving -> {
                    animateMapToLocation(
                        cameraPositionState,
                        mapState.locationLatLng()!!,
                        longAnimationDuration
                    )
                    viewModel.onZoomedToOrderPosition()
                }
                /**
                 * If user enables tracking both location and order at the same time
                 * And locations of user and order are available, map should animate to bounds
                 * showing both user and order at the same time
                 */
                mapState.canFollowUserAndOrder && locationSource.lastRegisteredLocation != null -> {
                    animateMapToBounds(
                        cameraPositionState,
                        LatLngBounds.Builder()
                            .include(locationSource.lastRegisteredLocation!!)
                            .include(mapState.locationLatLng()!!)
                            .build(),
                        shortAnimationDuration
                    )
                    viewModel.onZoomedToOrderPosition()
                }
                /**
                 * If it's already zoomed to the order, it should use the camera positions
                 * calculated by sdk-ui to perform a smooth animation between locations
                 */
                mapState.cameraPosition != null && !cameraPositionState.isMoving -> animateMapToLocation(
                    cameraPositionState,
                    mapState.cameraLatLng()!!,
                    shortAnimationDuration
                )
            }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            myLocationButtonEnabled = false
        ),
        properties = MapProperties(
            isMyLocationEnabled = locationPermissionState.status.isGranted
        ),
        locationSource = locationSource
    ) {
        if (mapState.location != null) {
            Marker(
                state = MarkerState(
                    position = mapState.locationLatLng()!!
                )
            )
        }
    }
}

@Suppress("SwallowedException")
private suspend fun animateMapToLocation(
    cameraPositionState: CameraPositionState,
    latLng: LatLng,
    durationMs: Int
) {
    try {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(latLng, mapZoomLevel),
            durationMs
        )
    } catch (e: CancellationException) {
        // Do Nothing
    }
}

@Suppress("SwallowedException")
private suspend fun animateMapToBounds(
    cameraPositionState: CameraPositionState,
    latLngBounds: LatLngBounds,
    durationMs: Int
) {
    try {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngBounds(latLngBounds, mapBoundsPadding),
            durationMs
        )
    } catch (e: CancellationException) {
        // Do Nothing
    }
}
