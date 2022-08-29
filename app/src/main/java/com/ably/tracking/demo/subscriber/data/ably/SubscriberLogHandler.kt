package com.ably.tracking.demo.subscriber.data.ably

import android.util.Log
import com.ably.tracking.logging.LogHandler
import com.ably.tracking.logging.LogLevel

object SubscriberLogHandler : LogHandler {

    override fun logMessage(level: LogLevel, message: String, throwable: Throwable?) {
        when (level) {
            LogLevel.VERBOSE -> Log.v("AssetTracker", "Message: $message, Throwable: $throwable")
            LogLevel.INFO -> Log.i("AssetTracker", "Message: $message, Throwable: $throwable")
            LogLevel.DEBUG -> Log.d("AssetTracker", "Message: $message, Throwable: $throwable")
            LogLevel.WARN -> Log.w("AssetTracker", "Message: $message, Throwable: $throwable")
            LogLevel.ERROR -> Log.e("AssetTracker", "Message: $message, Throwable: $throwable")
        }
    }
}
