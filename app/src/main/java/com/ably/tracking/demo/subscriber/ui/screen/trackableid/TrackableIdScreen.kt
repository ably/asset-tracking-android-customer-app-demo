package com.ably.tracking.demo.subscriber.ui.screen.trackableid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.Routes
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
        TrackableIdScreenContent(navController, viewModel)
    }
}

@Composable
fun TrackableIdScreenContent(
    navController: NavController,
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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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
                navController.navigate(Routes.Dashboard.path + viewState.value.trackableId)
            }
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}
