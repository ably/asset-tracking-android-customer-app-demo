package com.ably.tracking.demo.subscriber.common

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test


internal class FixedSizeMutableListTest{

    @Test
    fun `after calling add on view model new order is created`() = runTest {
        //given
        val fixedSizeMutableList = FixedSizeMutableList(4)
        fixedSizeMutableList.add(1)
        fixedSizeMutableList.add(1)
        fixedSizeMutableList.add(1)
        fixedSizeMutableList.add(1)

        //when
        fixedSizeMutableList.add(999)
        fixedSizeMutableList.add(999)
        val average = fixedSizeMutableList.average()

        //then
        assertThat(average).isEqualTo(500)
    }

}
