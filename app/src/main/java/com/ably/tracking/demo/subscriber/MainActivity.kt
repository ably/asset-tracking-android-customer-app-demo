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
            NavHost(navController = navController, startDestination = "trackableId") {
                composable("trackableId") { TrackableIdScreen(navController = navController) }
                composable("main") { MainScreen() }
            }
        }
    }
}
