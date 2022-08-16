package com.ably.tracking.demo.subscriber.common

import com.ably.tracking.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun Location?.distanceTo(location: LatLng?): Double? {
    if (this == null || location == null) {
        return null
    }

    val currentLatitudeInRadians = this.latitude.toRadians()
    val orderLatitudeInRadians = location.latitude.toRadians()
    val latitudeDeltaInRadians = (location.latitude - this.latitude).toRadians()
    val longitudeDeltaInRadians = (location.longitude - this.longitude).toRadians()

    val a = sin(latitudeDeltaInRadians / 2) * sin(latitudeDeltaInRadians / 2) +
        cos(currentLatitudeInRadians) * cos(orderLatitudeInRadians) *
        sin(longitudeDeltaInRadians / 2) * sin(longitudeDeltaInRadians / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val earthRadius = 6_371_000 // in metres
    return earthRadius * c // in metres
}

fun Double.toRadians() = this * Math.PI / 180
