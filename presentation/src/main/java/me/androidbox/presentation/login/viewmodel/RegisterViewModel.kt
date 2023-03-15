package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.presentation.login.screen.LoginScreenEvent
import me.androidbox.presentation.login.screen.LoginScreenState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val registerScreenMutableState = MutableStateFlow(LoginScreenState("", "", false, "", null, null))
    val registerScreenState = registerScreenMutableState.asStateFlow()

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

    private fun registerUser() {
        viewModelScope.launch {
            registerScreenMutableState.value = registerScreenState.value.copy(
                registrationResponseState = ResponseState.Loading
            )

            val responseState = registerUseCase.execute(
                fullName = registerScreenState.value.username,
                email = registerScreenState.value.email,
                password = registerScreenState.value.password)

            registerScreenMutableState.value = registerScreenState.value.copy(
                registrationResponseState = responseState
            )
        }
    }
}