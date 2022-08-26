package com.ably.tracking.demo.subscriber.common

fun String.canParseToDouble() =
    toDoubleOrNull() != null
