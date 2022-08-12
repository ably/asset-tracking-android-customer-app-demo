package com.ably.tracking.demo.subscriber.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun doOnCreateLifecycleEvent(onLifecycleEvent: () -> Unit) {
    doOnLifecycleEvent {
        if (it == Lifecycle.Event.ON_CREATE) {
            onLifecycleEvent()
        }
    }
}

@Composable
fun doOnLifecycleEvent(onLifecycleEvent: (Lifecycle.Event) -> Unit) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        observeLifecycleEvents(lifecycleOwner, onLifecycleEvent)
    }
}

private fun DisposableEffectScope.observeLifecycleEvents(
    lifecycleOwner: LifecycleOwner,
    onLifecycleEvent: (Lifecycle.Event) -> Unit
): DisposableEffectResult {
    val lifecycleObserver = LifecycleEventObserver { _, event ->
        onLifecycleEvent(event)
    }

    lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

    return onDispose {
        lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
    }
}
