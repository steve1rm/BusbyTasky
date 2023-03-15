package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.presentation.login.screen.AuthenticationScreenEvent
import me.androidbox.presentation.login.screen.AuthenticationScreenState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val registerScreenMutableState = MutableStateFlow(AuthenticationScreenState<Unit>("", "", false, "", null))
    val registerScreenState = registerScreenMutableState.asStateFlow()

    fun onRegistrationEvent(authenticationScreenEvent: AuthenticationScreenEvent) {
        when(authenticationScreenEvent) {
            is AuthenticationScreenEvent.OnEmailChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    email = authenticationScreenEvent.email
                )
            }
            is AuthenticationScreenEvent.OnUsernameChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    username = authenticationScreenEvent.username
                )
            }
            is AuthenticationScreenEvent.OnPasswordChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    password = authenticationScreenEvent.password
                )
            }
            AuthenticationScreenEvent.OnPasswordVisibilityChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    isPasswordVisible = !registerScreenState.value.isPasswordVisible
                )
            }
            AuthenticationScreenEvent.OnRegisterUser -> {
                registerUser()
            }
            AuthenticationScreenEvent.OnAuthenticationUser -> Unit
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            registerScreenMutableState.value = registerScreenState.value.copy(
                responseState = ResponseState.Loading)

            val responseState = registerUseCase.execute(
                fullName = registerScreenState.value.username,
                email = registerScreenState.value.email,
                password = registerScreenState.value.password)

            registerScreenMutableState.value = registerScreenState.value.copy(
                responseState = responseState
            )
        }
    }
}