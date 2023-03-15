package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.presentation.login.screen.LoginScreenEvent
import me.androidbox.presentation.login.screen.LoginScreenState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val registerMutableState: MutableStateFlow<ResponseState<Unit>?> = MutableStateFlow(null)
    val registrationState = registerMutableState.asStateFlow()

    private val registerScreenMutableState = MutableStateFlow<LoginScreenState>(LoginScreenState("", "", false, "", null))
    val registerScreenState = registerScreenMutableState.asStateFlow()

    private companion object {
        const val USERNAME = "username"
        const val EMAIL_ADDRESS = "email_address"
        const val PASSWORD = "password"
        const val IS_PASSWORD_VISIBLE = "is_password_visible"
    }

    val username = savedStateHandle.getStateFlow(USERNAME, "")
    val email = savedStateHandle.getStateFlow(EMAIL_ADDRESS, "")
    val isPasswordVisible = savedStateHandle.getStateFlow(IS_PASSWORD_VISIBLE, false)
    val password = savedStateHandle.getStateFlow(PASSWORD, "")

    fun onLoginEvent(loginScreenEvent: LoginScreenEvent) {
        when(loginScreenEvent) {
            is LoginScreenEvent.OnEmailChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    email = loginScreenEvent.email
                )
            }
            is LoginScreenEvent.OnUsernameChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    username = loginScreenEvent.username
                )
            }
            is LoginScreenEvent.OnPasswordChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    password = loginScreenEvent.password
                )
            }
            LoginScreenEvent.OnPasswordVisibilityChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    isPasswordVisible = !registerScreenState.value.isPasswordVisible
                )
            }
            LoginScreenEvent.OnRegisterUser -> {
                registerUser()
            }
            LoginScreenEvent.OnLoginUser -> Unit
        }
    }

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

    fun registerUser() {
        viewModelScope.launch {
            registerMutableState.value = ResponseState.Loading

            registerMutableState.value = registerUseCase.execute(
                fullName = username.value,
                email = email.value,
                password = password.value
            )
        }
    }
}