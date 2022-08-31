package com.ably.tracking.demo.subscriber.domain.secrets

interface Base64Encoder {
    fun encode(input: String): String
}
