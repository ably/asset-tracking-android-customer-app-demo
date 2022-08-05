package com.ably.tracking.demo.subscriber

sealed interface Routes {

    object CreateOrder : Routes {
        const val pathWithParams = "order/create/"
    }

    object Dashboard : Routes {
        const val path = "dashboard/"
    }
}
