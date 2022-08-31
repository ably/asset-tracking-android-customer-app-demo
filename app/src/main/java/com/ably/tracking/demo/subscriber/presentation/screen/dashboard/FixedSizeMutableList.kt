package com.ably.tracking.demo.subscriber.presentation.screen.dashboard

class FixedSizeMutableList(private val maxCapacity: Int) {

    private val innerList = mutableListOf<Long>()

    fun add(element: Long) {
        innerList.add(element)
        if (innerList.size > maxCapacity) {
            innerList.removeAt(0)
        }
    }

    fun average(): Long? =
        if (innerList.size == 0) {
            null
        } else {
            innerList.sum() / innerList.size
        }
}
