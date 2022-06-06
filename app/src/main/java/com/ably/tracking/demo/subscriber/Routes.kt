package com.ably.tracking.demo.subscriber

sealed interface Routes {

    object TrackableId : Routes {
        const val pathWithParams = "trackableId/"
    }

    object Main : Routes {
        const val paramTrackableId = "trackableId"
        const val path = "main/"
        const val pathWithParams = "main/{$paramTrackableId}"
    }
}
