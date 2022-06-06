package com.ably.tracking.demo.subscriber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ably.tracking.demo.subscriber.ui.screen.main.MainScreen
import com.ably.tracking.demo.subscriber.ui.screen.trackableid.TrackableIdScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Routes.TrackableId.pathWithParams
            ) {
                composable(Routes.TrackableId.pathWithParams) { TrackableIdScreen(navController = navController) }
                composable(Routes.Main.pathWithParams) { backStackEntry ->
                    MainScreen(
                        trackableId = backStackEntry.arguments!!.getString(Routes.Main.paramTrackableId)!!
                    )
                }
            }
        }
    }
}
