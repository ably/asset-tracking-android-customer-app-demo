package com.ably.tracking.demo.subscriber.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SingleButtonAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes buttonText: Int,
    onButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = title)
            )
        },
        text = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(text)
            )
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White,
                    disabledContentColor = Color.DarkGray,
                    contentColor = Color.Black
                ),
                onClick = onButtonClick,
            ) {
                Text(text = stringResource(id = buttonText))
            }
        }
    )
}
