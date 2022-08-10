package com.ably.tracking.demo.subscriber.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextField(
    @StringRes label: Int,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        label = { Text(stringResource(id = label)) },
    )
}
