package com.ably.tracking.demo.subscriber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ably.tracking.demo.subscriber.common.FusedLocationSource
import com.ably.tracking.demo.subscriber.ui.screen.Navigator
import com.ably.tracking.demo.subscriber.ui.screen.createorder.CreateOrderScreen
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.DashboardScreen
import com.ably.tracking.demo.subscriber.ui.screen.login.LoginScreen
import com.ably.tracking.demo.subscriber.ui.screen.orderarrived.OrderArrivedScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val locationSource: FusedLocationSource by inject()

    private val navigator: Navigator by inject()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            navigator.navController = navController
            NavHost(
                navController = navController,
                startDestination = Routes.Login.path
            ) {
                composable(Routes.Login.path) { LoginScreen() }
                composable(Routes.CreateOrder.path) { CreateOrderScreen() }
                composable(Routes.Dashboard.path) {
                    DashboardScreen(
                        locationSource = locationSource,
                        navController = navController
                    )
                }
                composable(Routes.OrderArrived.path) { OrderArrivedScreen() }
            }
        }
    }
}
