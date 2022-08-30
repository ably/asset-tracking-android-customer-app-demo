package com.ably.tracking.demo.subscriber.domain.secrets

import com.ably.tracking.demo.subscriber.domain.DeliveryServiceDataSource

class InMemorySecretsManager(
    private val deliveryServiceDataSource: DeliveryServiceDataSource,
    private val secretsStorage: SecretsStorage,
    private val base64Encoder: Base64Encoder
) : SecretsManager {

    companion object {
        private const val MAPBOX_TOKEN_KEY = "mapbox"
    }

    private val secrets = mutableMapOf<String, String>()

    override suspend fun loadSecrets(username: String, password: String?) {
        secretsStorage.writeUsername(username)

        val encodedAuthorization =
            if (password == null) {
                readAuthorization()
            } else {
                base64Encoder.encode("$username:$password")
            }

        secretsStorage.writeAuthorization(encodedAuthorization)
        secrets[MAPBOX_TOKEN_KEY] = deliveryServiceDataSource.getMapboxToken(encodedAuthorization)
    }

    override fun hasAuthorizationSecrets(): Boolean =
        getUsername() != null && getAuthorizationHeader() != null

    override fun getUsername(): String? = secretsStorage.readUsername()

    override fun getAuthorizationHeader(): String? = secretsStorage.readAuthorization()

    override fun getMapboxToken(): String = secrets[MAPBOX_TOKEN_KEY] ?: ""

    private fun readAuthorization() = secretsStorage.readAuthorization()!!
}
