package com.ably.tracking.demo.subscriber.ui.screen.createorder

import com.ably.tracking.demo.subscriber.common.canParseToDouble

data class CreateOrderScreenState(
    val trackableId: String = "",
    val fromLatitude: String = "51.1065859",
    val fromLongitude: String = "17.0312766",
    val toLatitude: String = "51.1065859",
    val toLongitude: String = "17.0312766",
    val showProgress: Boolean = false
) {
    val isConfirmButtonEnabled: Boolean
        get() = fromLatitude.canParseToDouble() &&
            fromLongitude.canParseToDouble() &&
            toLatitude.canParseToDouble() &&
            toLongitude.canParseToDouble()
}
