package com.ably.tracking.demo.subscriber.data.secrets

import android.util.Base64
import com.ably.tracking.demo.subscriber.domain.secrets.Base64Encoder

class AndroidBase64Encoder : Base64Encoder {
    override fun encode(input: String) =
        String(Base64.encode(input.toByteArray(), Base64.DEFAULT)).replace("\n", "")
}
