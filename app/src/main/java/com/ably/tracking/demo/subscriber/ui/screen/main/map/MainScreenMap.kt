package com.ably.tracking.demo.subscriber.ui.screen.main.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ably.tracking.demo.subscriber.ui.screen.main.MainViewModel
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

const val longAnimationDuration = 1000
const val shortAnimationDuration = 100
const val mapZoomLevel = 16F

@Composable
fun MainScreenMap(
    viewModel: MainViewModel
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
                 * and not already zooming, it should zoom to the trackable location,
                 * before we start animating between the provided camera positions
                 */
                mapState.isInInitialState && !cameraPositionState.isMoving -> {
                    animateMapToLocation(
                        cameraPositionState,
                        mapState.locationLatLng()!!,
                        longAnimationDuration
                    )
                    viewModel.onZoomedToTrackablePosition()
                }
                /**
                 * If it's already zoomed to the package, it should use the camera positions
                 * calculated by sdk-ui to perform a smooth animation between locations
                 */
                mapState.cameraPosition != null -> animateMapToLocation(
                    cameraPositionState,
                    mapState.cameraLatLng()!!,
                    shortAnimationDuration
                )
            }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        if (mapState.location != null) {
            Circle(
                center = mapState.locationLatLng()!!,
                radius = 10.0,
                strokeWidth = 4F,
                strokeColor = Color.Black,
                fillColor = Color.Blue,
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
