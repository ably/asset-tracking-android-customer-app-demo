package com.ably.tracking.demo.subscriber.presentation.navigation

sealed interface Routes {

    object Login : Routes {
        const val path = "login/"
    }

    object CreateOrder : Routes {
        const val path = "order/create/"
    }

    object Dashboard : Routes {
        const val path = "dashboard/"
    }

    object OrderArrived : Routes {
        const val path = "order/arrived"
    }
}