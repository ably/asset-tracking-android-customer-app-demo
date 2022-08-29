package com.ably.tracking.demo.subscriber.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.presentation.theme.AATSubscriberDemoTheme

@Preview
@Composable
fun AATAppBar() = AATSubscriberDemoTheme {
    TopAppBar(
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.header_logo_with_title
            ),
            contentDescription = stringResource(
                id = R.string.image_content_description_header_logo_with_title
            )
        )
    }
}
