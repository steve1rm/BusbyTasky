package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.NetworkResponseState
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val registerMutableState: MutableStateFlow<NetworkResponseState<Unit>?> = MutableStateFlow(null)
    val registrationState = registerMutableState.asStateFlow()

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

    fun registerUser(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            registerMutableState.value = NetworkResponseState.Loading

            registerMutableState.value = registerUseCase.execute(
                fullName = fullName,
                email = email,
                password = password
            )
        }
    }
}