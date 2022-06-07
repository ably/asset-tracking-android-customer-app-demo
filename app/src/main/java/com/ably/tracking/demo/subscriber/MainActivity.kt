package com.ably.tracking.demo.subscriber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ably.tracking.demo.subscriber.common.LocationProviderLocationSource
import com.ably.tracking.demo.subscriber.ui.screen.dashboard.DashboardScreen
import com.ably.tracking.demo.subscriber.ui.screen.trackableid.TrackableIdScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationSource: LocationProviderLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Routes.TrackableId.pathWithParams
            ) {
                composable(Routes.TrackableId.pathWithParams) { TrackableIdScreen(navController = navController) }
                composable(Routes.Dashboard.pathWithParams) { backStackEntry ->
                    DashboardScreen(
                        trackableId = backStackEntry.arguments!!.getString(Routes.Dashboard.paramTrackableId)!!,
                        locationSource = locationSource
                    )
                }
            }
        }
    }
}
