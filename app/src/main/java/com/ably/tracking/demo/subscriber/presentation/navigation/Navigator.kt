package com.ably.tracking.demo.subscriber.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

class Navigator {

    lateinit var navController: NavController

    fun navigateToCreateOrder() {
        val navOptions = NavOptions.Builder().setPopUpTo(Routes.Login.path, true).build()
        navController.navigate(Routes.CreateOrder.path, navOptions)
    }

    fun navigateToRecreateOrder() {
        val navOptions = NavOptions.Builder().setPopUpTo(Routes.CreateOrder.path, true).build()
        navController.navigate(Routes.CreateOrder.path, navOptions)
    }

    fun navigateToDashboard() {
        navController.navigate(Routes.Dashboard.path)
    }

    fun navigateToOrderArrived() {
        navController.navigate(Routes.OrderArrived.path)
    }
}
