package com.ably.tracking.demo.subscriber.presentation.screen.dashboard

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ably.tracking.demo.subscriber.domain.orders.OrderState

class DashboardScreenStatePreview : PreviewParameterProvider<DashboardScreenState> {
    override val values = sequenceOf(
        DashboardScreenState(
            orderState = OrderState(OrderState.State.Failed, "Error")
        )
    )
}
