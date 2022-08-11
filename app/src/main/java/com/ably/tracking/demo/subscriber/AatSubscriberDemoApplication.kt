package com.ably.tracking.demo.subscriber

import android.app.Application
import com.ably.tracking.demo.subscriber.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AatSubscriberDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@AatSubscriberDemoApplication)
            modules(appModule)
        }
    }
}
