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
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.DashboardScreen
import com.ably.tracking.demo.subscriber.ui.screen.trackableid.TrackableIdScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationSource: FusedLocationSource

    @Inject
    lateinit var navigator: Navigator

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            navigator.navController = navController
            NavHost(
                navController = navController,
                startDestination = Routes.TrackableId.pathWithParams
            ) {
                composable(Routes.TrackableId.pathWithParams) { TrackableIdScreen() }
                composable(Routes.Dashboard.path) {
                    DashboardScreen(
                        locationSource = locationSource,
                        navController = navController
                    )
                }
            }
        }
    }
}
