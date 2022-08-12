package com.ably.tracking.demo.subscriber.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextButton(@StringRes text: Int, isEnabled: Boolean = true, onClick: () -> Unit) {
    androidx.compose.material.TextButton(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (isEnabled) Color.White else Color.Gray,
            disabledContentColor = Color.DarkGray,
            contentColor = Color.Black
        ),
        onClick = onClick
    ) {
        Text(text = stringResource(id = text))
    }
}
