package com.ably.tracking.demo.subscriber.domain

data class OrderState(
    val state: State,
    val errorMessage: String?
) {
    enum class State {
        Online, Publishing, Offline, Failed
    }

    fun isWorking() = state == State.Online || state == State.Publishing
}
