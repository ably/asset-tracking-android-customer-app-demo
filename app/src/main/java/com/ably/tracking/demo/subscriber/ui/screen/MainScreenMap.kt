package com.ably.tracking.demo.subscriber.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreenMap(
    viewModel: MainViewModel
) = AATSubscriberDemoTheme {
    val viewState = viewModel.state.collectAsState().value
    val trackableLocation = viewState.locationLatLng()
    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        if (trackableLocation != null) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    trackableLocation,
                    16f
                )
            )
            Circle(
                center = trackableLocation,
                radius = viewState.trackableLocation!!.location.accuracy.toDouble(),
                strokeColor = Color.Blue,
                fillColor = Color.Blue,
            )
        }
    }
}
