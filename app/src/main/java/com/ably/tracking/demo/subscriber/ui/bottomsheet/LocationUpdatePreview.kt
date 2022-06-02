package com.ably.tracking.demo.subscriber.ui.bottomsheet

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ably.tracking.Location
import com.ably.tracking.LocationUpdate

class LocationUpdatePreview : PreviewParameterProvider<LocationUpdate> {
    override val values = sequenceOf(
        LocationUpdate(
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
        )
    )
}
