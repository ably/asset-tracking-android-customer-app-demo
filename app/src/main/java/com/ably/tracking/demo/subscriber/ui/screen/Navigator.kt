package com.ably.tracking.demo.subscriber.ui.screen

import androidx.navigation.NavController
import com.ably.tracking.demo.subscriber.Routes

class Navigator {

    lateinit var navController: NavController

    fun navigateToCreateOrder() {
        navController.navigate(Routes.CreateOrder.path)
    }

    fun navigateToDashboard() {
        navController.navigate(Routes.Dashboard.path)
    }

    fun navigateToOrderArrived() {

    }
}
