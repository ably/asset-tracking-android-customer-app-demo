package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.ui.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.ui.widget.AATAppBar

@Composable
fun TrackableIdScreen(
    navController: NavController,
    viewModel: TrackableIdViewModel = viewModel()
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

        OutlinedTextField(
            value = viewState.value.trackableId,
            onValueChange = { value -> viewModel.onTrackableIdChanged(value) },
            label = { Text(stringResource(id = R.string.trackable_id)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = Color.White,
                focusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                cursorColor = MaterialTheme.colors.secondary
            )
        )
    }
}
