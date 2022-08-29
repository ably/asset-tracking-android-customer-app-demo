package com.ably.tracking.demo.subscriber.presentation.screen.createorder

data class CreateOrderScreenState(
    val orderId: String = "",
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

    private fun String.canParseToDouble() =
        toDoubleOrNull() != null

}
