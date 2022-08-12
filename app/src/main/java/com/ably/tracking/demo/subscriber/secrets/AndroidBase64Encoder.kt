package com.ably.tracking.demo.subscriber.secrets

import android.util.Base64

class AndroidBase64Encoder : Base64Encoder {
    override fun encode(input: String) =
        String(Base64.encode(input.toByteArray(), Base64.DEFAULT)).replace("\n", "")
}
