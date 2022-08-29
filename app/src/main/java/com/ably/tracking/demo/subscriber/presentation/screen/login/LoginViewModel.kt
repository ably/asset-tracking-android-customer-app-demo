package com.ably.tracking.demo.subscriber.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ably.tracking.demo.subscriber.presentation.navigation.Navigator
import com.ably.tracking.demo.subscriber.secrets.SecretsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val secretsManager: SecretsManager,
    private val navigator: Navigator
) : ViewModel() {

    val state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())

    fun onCreated() = viewModelScope.launch {
        val username = secretsManager.getUsername() ?: ""
        updateState { it.copy(username = username) }

        if (secretsManager.hasAuthorizationSecrets()) {
            tryLoadSecrets(username, null)
        }
    }

    fun onFetchingSecretsFailedDialogClosed() = viewModelScope.launch {
        updateState { it.copy(showFetchingSecretsFailedDialog = false) }
    }

    fun onUsernameChanged(value: String) = viewModelScope.launch {
        updateState {
            it.copy(username = value)
        }
    }

    fun onPasswordChanged(value: String) = viewModelScope.launch {
        updateState {
            it.copy(password = value)
        }
    }

    fun onContinueClicked() = viewModelScope.launch {
        val stateValue = state.value
        tryLoadSecrets(stateValue.username, stateValue.password)
    }

    private suspend fun tryLoadSecrets(username: String, password: String?) {
        updateState { it.copy(showProgress = true) }
        try {
            secretsManager.loadSecrets(username, password)
            navigator.navigateToCreateOrder()
        } catch (_: Exception) {
            updateState { it.copy(showFetchingSecretsFailedDialog = true) }
        } finally {
            updateState { it.copy(showProgress = false) }
        }
    }

    private suspend fun updateState(update: (LoginScreenState) -> LoginScreenState) {
        state.emit(update(state.value))
    }
}
