package com.ably.tracking.demo.subscriber.ui.screen.main.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ably.tracking.demo.subscriber.ui.screen.main.MainViewModel
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreenMap(
    viewModel: MainViewModel
) = AATSubscriberDemoTheme {
    val zoomLevel = 16f
    val mapState = viewModel.mapState.collectAsState().value
    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // FIXME: investigate if it's possible to call `animate()` instead of `move()`
        if (mapState.location != null && mapState.cameraPosition != null) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    mapState.cameraLatLng()!!,
                    zoomLevel
                )
            )
            Circle(
                center = mapState.locationLatLng()!!,
                radius = mapState.location.accuracy.toDouble(),
                strokeColor = Color.Blue,
                fillColor = Color.Blue,
            )
        }
    }
}
