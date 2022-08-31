package com.ably.tracking.demo.subscriber.presentation.screen.orderarrived

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.subscriber.R
import com.ably.tracking.demo.subscriber.presentation.theme.AATSubscriberDemoTheme
import com.ably.tracking.demo.subscriber.presentation.widget.AATAppBar
import com.ably.tracking.demo.subscriber.presentation.widget.StyledTextButton
import org.koin.androidx.compose.getViewModel

@Composable
@Preview
fun OrderArrivedScreen(viewModel: OrderArrivedViewModel = getViewModel()) = AATSubscriberDemoTheme {
    Scaffold(
        topBar = { AATAppBar() }
    ) {
        CreateOrderScreenContent(viewModel)
    }
}

@Composable
fun CreateOrderScreenContent(viewModel: OrderArrivedViewModel) = AATSubscriberDemoTheme {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.order_arrived_message)
        )
        StyledTextButton(
            text = R.string.order_arrived_button_text,
            onClick = viewModel::onCreateNewOrderClick
        )
    }
}
