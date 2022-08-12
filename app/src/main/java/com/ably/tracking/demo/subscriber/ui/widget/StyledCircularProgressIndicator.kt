package com.ably.tracking.demo.subscriber.ui.widget

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StyledCircularProgressIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.size(64.dp),
        strokeWidth = 6.dp
    )
}
