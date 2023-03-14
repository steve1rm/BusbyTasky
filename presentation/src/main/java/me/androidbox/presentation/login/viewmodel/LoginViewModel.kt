package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.presentation.login.screen.LoginScreenEvent
import me.androidbox.presentation.login.screen.LoginScreenState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val loginResponseMutableState: MutableStateFlow<ResponseState<Login>?> = MutableStateFlow(null)
    val loginResponseState = loginResponseMutableState.asStateFlow()

    private val loginScreenMutableState = MutableStateFlow(LoginScreenState("", "", false))
    val loginScreenState = loginScreenMutableState.asStateFlow()

    fun onLoginEvent(loginScreenEvent: LoginScreenEvent) {
        when(loginScreenEvent) {
            is LoginScreenEvent.OnEmailChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                    email = loginScreenEvent.email
                )
            }
            is LoginScreenEvent.OnPasswordChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                    password = loginScreenEvent.password
                )
            }
            is LoginScreenEvent.OnPasswordVisibilityChanged -> {
                loginScreenMutableState.value = loginScreenState.value.copy(
                        isPasswordVisible = !loginScreenState.value.isPasswordVisible
                    )
            }
            LoginScreenEvent.OnLoginUser -> {
                loginUser(loginScreenState.value.email, loginScreenState.value.password)
            }
        }
    }
    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginResponseMutableState.value = loginUseCase.execute(email, password)
        }
    }

    fun saveCurrentUserDetails(login: Login) {
        /* Will call use-case to save user login details to shared preferences WIP */
    }
}