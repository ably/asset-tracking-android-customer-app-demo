package com.ably.tracking.demo.subscriber.secrets

interface Base64Encoder {
    fun encode(input: String): String
}
