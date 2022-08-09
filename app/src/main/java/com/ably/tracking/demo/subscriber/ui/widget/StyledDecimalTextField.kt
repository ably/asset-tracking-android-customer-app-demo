package com.ably.tracking.demo.subscriber.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun StyledDecimalTextField(@StringRes label: Int, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
        label = { Text(stringResource(id = label)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLabelColor = Color.White,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
            cursorColor = MaterialTheme.colors.secondary
        )
    )
}
