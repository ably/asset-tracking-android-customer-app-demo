package com.ably.tracking.demo.subscriber.ui.screen

import androidx.navigation.NavController
import com.ably.tracking.demo.subscriber.Routes
import javax.inject.Singleton

@Singleton
class Navigator {

    lateinit var navController: NavController

    fun navigateToDashboard() {
        navController.navigate(Routes.Dashboard.path)
    }

    fun navigateToCreateOrder() {
        navController.navigate(Routes.CreateOrder.path)
    }
}
