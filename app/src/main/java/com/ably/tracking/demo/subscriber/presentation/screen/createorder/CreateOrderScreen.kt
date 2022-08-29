package com.ably.tracking.demo.subscriber.presentation.screen.createorder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.presentation.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.presentation.widget.AATAppBar
import com.ably.tracking.demo.subscriber.presentation.widget.StyledCircularProgressIndicator
import com.ably.tracking.demo.subscriber.presentation.widget.StyledTextButton
import com.ably.tracking.demo.subscriber.presentation.widget.StyledTextField
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateOrderScreen(
    viewModel: CreateOrderViewModel = getViewModel()
) = AATSubscriberDemoTheme {
    Scaffold(
        topBar = { AATAppBar() }
    ) {
        CreateOrderScreenContent(viewModel)
    }
}

@Composable
fun CreateOrderScreenContent(
    viewModel: CreateOrderViewModel
) = AATSubscriberDemoTheme {
    val viewState: State<CreateOrderScreenState> = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewState.value.showProgress) {
            StyledCircularProgressIndicator()
        } else {
            OrderDataForm(viewModel, viewState)
        }
    }
}

@Composable
fun OrderDataForm(viewModel: CreateOrderViewModel, viewState: State<CreateOrderScreenState>) {
    StyledTextField(
        label = R.string.order_from_latitude_label,
        value = viewState.value.fromLatitude,
        keyboardType = KeyboardType.Decimal
    ) { viewModel.onFromLatitudeChanged(it) }
    StyledTextField(
        label = R.string.order_from_longitude_label,
        value = viewState.value.fromLongitude,
        keyboardType = KeyboardType.Decimal
    ) { viewModel.onFromLongitudeChanged(it) }
    StyledTextField(
        label = R.string.order_to_latitude_label,
        value = viewState.value.toLatitude,
        keyboardType = KeyboardType.Decimal
    ) { viewModel.onToLatitudeChanged(it) }
    StyledTextField(
        label = R.string.order_to_longitude_label,
        value = viewState.value.toLongitude,
        keyboardType = KeyboardType.Decimal
    ) { viewModel.onToLongitudeChanged(it) }
    StyledTextButton(
        text = R.string.create_order,
        isEnabled = viewState.value.isConfirmButtonEnabled,
        onClick = viewModel::onClick
    )
}
