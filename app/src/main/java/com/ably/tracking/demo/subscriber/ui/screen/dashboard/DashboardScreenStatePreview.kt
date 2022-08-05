package com.ably.tracking.demo.subscriber.ui.screen.dashboard

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ably.tracking.ErrorInformation
import com.ably.tracking.TrackableState

class DashboardScreenStatePreview : PreviewParameterProvider<DashboardScreenState> {
    override val values = sequenceOf(
        DashboardScreenState(
            trackableState = TrackableState.Failed(
                errorInformation = ErrorInformation("Error")
            )
        )
    )
}
