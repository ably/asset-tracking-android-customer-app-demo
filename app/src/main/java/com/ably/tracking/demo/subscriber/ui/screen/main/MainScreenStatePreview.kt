package com.ably.tracking.demo.subscriber.ui.screen.main

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ably.tracking.ErrorInformation
import com.ably.tracking.TrackableState

class MainScreenStatePreview : PreviewParameterProvider<MainScreenState> {
    override val values = sequenceOf(
        MainScreenState(
            isAssetTrackerReady = true,
            trackableState = TrackableState.Failed(
                errorInformation = ErrorInformation("Error")
            )
        )
    )
}
