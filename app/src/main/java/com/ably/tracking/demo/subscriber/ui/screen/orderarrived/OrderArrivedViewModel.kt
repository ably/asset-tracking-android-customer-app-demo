package com.ably.tracking.demo.subscriber.ui.screen.orderarrived

import androidx.lifecycle.ViewModel
import com.ably.tracking.demo.subscriber.ui.screen.Navigator

class OrderArrivedViewModel(private val navigator: Navigator) : ViewModel() {

    fun onCreateNewOrderClick() {
        navigator.navigateToRecreateOrder()
    }
}
