package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.domain.authentication.usecase.SaveCurrentUserUseCase
import me.androidbox.domain.login.usecase.ValidateEmailUseCase
import me.androidbox.presentation.login.screen.AuthenticationScreenEvent
import me.androidbox.presentation.login.screen.AuthenticationScreenState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val saveCurrentUserUseCase: SaveCurrentUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val loginScreenMutableState = MutableStateFlow(AuthenticationScreenState<AuthenticatedUser>())
    val loginScreenState = loginScreenMutableState.asStateFlow()

/*
    val email = savedStateHandle.getStateFlow<String>("email", "")
    val password = savedStateHandle.getStateFlow<String>("password", "")

    val state = combine(email, password) { email, password ->
        loginScreenMutableState.value = loginScreenState.value.copy(
            email = email,
            password = password,
            isValidEmail = validateEmailUseCase.execute(email),
            isValidPassword = validateEmailUseCase.execute(password)
        )
    }
*/

    fun onLoginEvent(authenticationScreenEvent: AuthenticationScreenEvent) {
        when(authenticationScreenEvent) {
            is AuthenticationScreenEvent.OnEmailChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                    email = authenticationScreenEvent.email,
                    isValidEmail = validateEmailUseCase.execute(authenticationScreenEvent.email)
                )
            }
            is AuthenticationScreenEvent.OnPasswordChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                    password = authenticationScreenEvent.password
                )
            }
            is AuthenticationScreenEvent.OnPasswordVisibilityChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                        isPasswordVisible = !loginScreenState.value.isPasswordVisible
                    )
            }
            AuthenticationScreenEvent.OnAuthenticationUser -> {
                loginScreenMutableState.update { _ ->
                    loginScreenState.value.copy(
                        responseState = ResponseState.Loading
                    )
                }
                loginUser(loginScreenState.value.email, loginScreenState.value.password)
            }
            is AuthenticationScreenEvent.OnLoading -> {
                loginScreenMutableState.update { _ ->
                    loginScreenState.value.copy(
                        isLoading = authenticationScreenEvent.isLoading
                    )
                }
            }

            AuthenticationScreenEvent.OnRegisterUser -> Unit
            is AuthenticationScreenEvent.OnUsernameChanged -> Unit
        }
    }
    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val loginResponseState = loginUseCase.execute(email, password)

            if (loginResponseState is ResponseState.Success) {
                saveCurrentUserDetails(loginResponseState.data)
            }

            loginScreenMutableState.value  = loginScreenState.value.copy(
                responseState = loginResponseState,
                isLoading = false)
        }
    }

    private fun saveCurrentUserDetails(authenticatedUser: AuthenticatedUser) {
        saveCurrentUserUseCase.execute(authenticatedUser)
    }
}