package com.ably.tracking.demo.subscriber.secrets

interface SecretsStorage {
    fun readUsername(): String?
    fun writeUsername(value: String)

    fun readAuthorization(): String?
    fun writeAuthorization(value: String)
}
