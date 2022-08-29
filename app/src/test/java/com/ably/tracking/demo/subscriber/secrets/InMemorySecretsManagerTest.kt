package com.ably.tracking.demo.subscriber.secrets

import com.ably.tracking.demo.subscriber.data.api.FakeDeliveryServiceDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class InMemorySecretsManagerTest {

    private val deliveryServiceApiSource = FakeDeliveryServiceDataSource()

    private val secretsStorage = FakeSecretsStorage()

    private val base64Encoder = JavaBase64Encoder()

    private val inMemorySecretsManager =
        InMemorySecretsManager(
            deliveryServiceApiSource,
            secretsStorage,
            base64Encoder
        )

    @Test
    fun `loadSecrets stores username and encoded authorization header in storage`() = runTest {
        // given
        val username = "rider"
        val password = "password"

        // when
        inMemorySecretsManager.loadSecrets(username, password)

        // then
        assertThat(secretsStorage.username)
            .isEqualTo("rider")
        assertThat(secretsStorage.authorization)
            .isEqualTo("cmlkZXI6cGFzc3dvcmQ=")
    }

    @Test
    fun `getUsername returns value from secretsStorage`() = runTest {
        // given
        secretsStorage.username = "rider"

        // when
        val username = inMemorySecretsManager.getUsername()

        // then
        assertThat(username)
            .isEqualTo("rider")
    }

    @Test
    fun `stored authorization header is used to make calls`() = runTest {
        // given
        secretsStorage.authorization = "cmlkZXI6cGFzc3dvcmQ"

        // when
        inMemorySecretsManager.loadSecrets("", null)

        // then
        assertThat(deliveryServiceApiSource.lastAuthorizationHeader)
            .isEqualTo("cmlkZXI6cGFzc3dvcmQ")
    }

    @Test
    fun `getMapboxToken return token from api after loadSecrets`() = runTest {
        // given
        val username = "rider"
        val password = "password"
        deliveryServiceApiSource.mapboxToken = "notARealMapboxToken"

        // when
        inMemorySecretsManager.loadSecrets(username, password)
        val mapboxToken = inMemorySecretsManager.getMapboxToken()

        // then
        assertThat(mapboxToken)
            .isEqualTo("notARealMapboxToken")
    }
}
