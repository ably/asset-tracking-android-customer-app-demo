package com.ably.tracking.demo.subscriber.api

import com.ably.tracking.demo.subscriber.BuildConfig
import com.ably.tracking.demo.subscriber.domain.GeoCoordinates
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
//@Ignore("Ignoring as those tests perform actual api calls")
internal class ApiDeliveryServiceDataSourceTest {

    private val okHttpClient = buildOkHttpClient()

    private val retrofit =
        buildRetrofit(okHttpClient, BuildConfig.FIREBASE_REGION, BuildConfig.FIREBASE_PROJECT_NAME)

    private val deliveryServiceApi = buildDeliveryServiceApi(retrofit)

    private val deliveryServiceApiSource = ApiDeliveryServiceDataSource(deliveryServiceApi)

    private val authorizationHeader = BuildConfig.AUTHORIZATION_HEADER_BASE_64

    @Test
    fun `call to getAblyToken returns non-empty value`() = runTest {
        // given

        // when
        val ablyToken = deliveryServiceApiSource.getAblyToken(authorizationHeader)

        // then
        assertThat(ablyToken)
            .isNotEmpty()
    }

    @Test
    fun `call to createOrder returns non-empty value`() = runTest {
        // given
        val from = GeoCoordinates(51.1065859, 17.0312766)
        val to = GeoCoordinates(51.1114666, 17.025932)

        // when
        val result = deliveryServiceApiSource.createOrder(authorizationHeader, from, to)

        // then
        assertThat(result.id)
            .isNotNull()
        assertThat(result.ablyToken)
            .isNotNull()
    }
}
