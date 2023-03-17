package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.domain.authentication.usecase.SaveCurrentUserUseCase
import me.androidbox.presentation.login.screen.AuthenticationScreenEvent
import me.androidbox.presentation.login.screen.AuthenticationScreenState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveCurrentUserUseCase: SaveCurrentUserUseCase
) : ViewModel() {

    private val loginScreenMutableState = MutableStateFlow(AuthenticationScreenState<AuthenticatedUser>())
    val loginScreenState = loginScreenMutableState.asStateFlow()

    fun onLoginEvent(authenticationScreenEvent: AuthenticationScreenEvent) {
        when(authenticationScreenEvent) {
            is AuthenticationScreenEvent.OnEmailChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                    email = authenticationScreenEvent.email
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
                loginUser(loginScreenState.value.email, loginScreenState.value.password)
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
                responseState = loginResponseState)
        }
    }

    private fun saveCurrentUserDetails(authenticatedUser: AuthenticatedUser) {
        saveCurrentUserUseCase.execute(authenticatedUser)
    }
}