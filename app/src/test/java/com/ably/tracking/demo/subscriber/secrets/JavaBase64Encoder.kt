package com.ably.tracking.demo.subscriber.secrets

import java.util.Base64

class JavaBase64Encoder : Base64Encoder {
    override fun encode(input: String) =
        String(Base64.getEncoder().encode(input.toByteArray()))
}
