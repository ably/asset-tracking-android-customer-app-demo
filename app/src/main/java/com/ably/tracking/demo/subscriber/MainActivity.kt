package com.ably.tracking.demo.subscriber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ably.tracking.demo.subscriber.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BuildConfig.ABLY_API_KEY
        setContent { MainScreen() }
    }
}