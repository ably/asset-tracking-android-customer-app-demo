package com.ably.tracking.demo.subscriber.presentation.screen.dashboard.bottomsheet

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ably.tracking.Accuracy
import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate
import com.ably.tracking.Resolution

class LocationUpdatePreview : PreviewParameterProvider<LocationUpdateBottomSheetData> {
    override val values = sequenceOf(
        LocationUpdateBottomSheetData(
            locationUpdate = LocationUpdate(
                location = Location(
                    latitude = 123.0,
                    longitude = 123.0,
                    altitude = 123.0,
                    accuracy = 10.0F,
                    bearing = 10.0F,
                    speed = 10.0F,
                    time = 123
                ),
                skippedLocations = emptyList()
            ),
            resolution = Resolution(
                accuracy = Accuracy.HIGH,
                minimumDisplacement = 1000.0,
                desiredInterval = 500
            ),
            lastLocationUpdateInterval = 100,
            averageLocationUpdateInterval = 300,
            remainingDistance = 150.0
        )
    )
}
