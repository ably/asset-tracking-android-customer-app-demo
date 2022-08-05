package com.ably.tracking.demo.subscriber

sealed interface Routes {

    object TrackableId : Routes {
        const val pathWithParams = "trackableId/"
    }

    object Dashboard : Routes {
        const val path = "dashboard/"
    }
}
