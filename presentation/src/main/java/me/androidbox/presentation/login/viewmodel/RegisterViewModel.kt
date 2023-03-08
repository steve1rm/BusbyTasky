package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private companion object {
        const val USERNAME = "username"
        const val EMAIL_ADDRESS = "email_address"
        const val PASSWORD = "password"
        const val IS_PASSWORD_VISIBLE = "is_password_visible"
    }

    val username = savedStateHandle.getStateFlow(USERNAME, "")
    val emailAddress = savedStateHandle.getStateFlow(EMAIL_ADDRESS, "")
    val isPasswordVisible = savedStateHandle.getStateFlow(IS_PASSWORD_VISIBLE, false)
    val password = savedStateHandle.getStateFlow(PASSWORD, "")

    fun onUsernameChanged(newUsername: String) {
        savedStateHandle[USERNAME] = newUsername
    }

    fun onEmailAddress(emailAddress: String) {
        savedStateHandle[EMAIL_ADDRESS] = emailAddress
    }

    fun onPasswordChanged(newPassword: String) {
        savedStateHandle[PASSWORD] = newPassword
    }

    fun onPasswordVisibilityChanged() {
        savedStateHandle[IS_PASSWORD_VISIBLE] = !isPasswordVisible.value
    }
}