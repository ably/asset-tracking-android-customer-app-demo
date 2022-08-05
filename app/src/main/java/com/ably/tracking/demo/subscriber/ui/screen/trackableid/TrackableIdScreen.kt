package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.ui.widget.AATAppBar
import com.ably.tracking.demo.subscriber.ui.widget.StyledDecimalTextField

@Composable
fun TrackableIdScreen(
    viewModel: TrackableIdViewModel = hiltViewModel()
) = AATSubscriberDemoTheme {
    Scaffold(
        topBar = { AATAppBar() }
    ) {
        TrackableIdScreenContent(viewModel)
    }
}

@Composable
fun TrackableIdScreenContent(
    viewModel: TrackableIdViewModel
) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val viewState: State<TrackableIdScreenState> = viewModel.state.collectAsState()
        StyledDecimalTextField(
            label = R.string.order_from_latitude_label,
            value = viewState.value.fromLatitude,
            onValueChange = viewModel::onFromLatitudeChanged
        )
        StyledDecimalTextField(
            label = R.string.order_from_longitude_label,
            value = viewState.value.fromLongitude,
            onValueChange = viewModel::onFromLongitudeChanged
        )
        StyledDecimalTextField(
            label = R.string.order_to_latitude_label,
            value = viewState.value.toLatitude,
            onValueChange = viewModel::onToLatitudeChanged
        )
        StyledDecimalTextField(
            label = R.string.order_to_longitude_label,
            value = viewState.value.toLongitude,
            onValueChange = viewModel::onToLongitudeChanged
        )
        TextButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(48.dp),
            enabled = viewState.value.isConfirmButtonEnabled,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = if (viewState.value.isConfirmButtonEnabled) Color.White else Color.Gray,
                disabledContentColor = Color.DarkGray,
                contentColor = Color.Black
            ),
            onClick = {
                viewModel.onClick()
            }
        ) {
            Text(text = stringResource(id = R.string.create_order))
        }
    }
}
