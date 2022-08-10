package com.ably.tracking.demo.subscriber.secrets

interface SecretsManager {
    suspend fun loadSecrets(username: String, password: String?)
    fun hasAuthorizationSecrets(): Boolean
    fun getUsername(): String?
    fun getAuthorizationHeader(): String?
    fun getMapboxToken(): String
}
