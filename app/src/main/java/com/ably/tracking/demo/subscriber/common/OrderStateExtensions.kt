package com.ably.tracking.demo.subscriber.common

import androidx.annotation.StringRes
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.domain.OrderState

@StringRes
fun OrderState.toStringRes(): Int = when (this.state) {
    OrderState.State.Online -> R.string.order_state_online
    OrderState.State.Publishing -> R.string.order_state_publishing
    OrderState.State.Failed -> R.string.order_state_failed
    OrderState.State.Offline -> R.string.order_state_offline
}
