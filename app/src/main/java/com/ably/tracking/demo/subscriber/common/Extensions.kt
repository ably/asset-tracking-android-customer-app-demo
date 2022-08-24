package com.ably.tracking.demo.subscriber.common

import androidx.annotation.StringRes
import com.ably.tracking.TrackableState
import com.ably.tracking.demo.subscriber.R

@StringRes
fun TrackableState.toStringRes(): Int = when (this) {
    is TrackableState.Online -> R.string.order_state_online
    is TrackableState.Publishing -> R.string.order_state_publishing
    is TrackableState.Failed -> R.string.order_state_failed
    is TrackableState.Offline -> R.string.order_state_offline
}

fun String.canParseToDouble() =
    toDoubleOrNull() != null
