package com.ably.tracking.demo.subscriber.ui.screen.login

data class LoginScreenState(
    val username: String = "",
    val password: String = "",
    val showFetchingSecretsFailedDialog: Boolean = false,
    val showProgress: Boolean = false
)
