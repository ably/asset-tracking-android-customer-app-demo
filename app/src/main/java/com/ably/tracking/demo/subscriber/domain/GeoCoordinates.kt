package com.ably.tracking.demo.subscriber.domain

data class GeoCoordinates(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun fromStrings(latitude: String, longitude: String)=
            GeoCoordinates(latitude.toDouble(), longitude.toDouble())
    }
}
