package com.ably.tracking.demo.subscriber.presentation.screen.orderarrived

import androidx.lifecycle.ViewModel
import com.ably.tracking.demo.subscriber.presentation.navigation.Navigator

class OrderArrivedViewModel(private val navigator: Navigator) : ViewModel() {

    fun onCreateNewOrderClick() {
        navigator.navigateToRecreateOrder()
    }
}
