package me.androidbox.presentation.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.LoginUser
import me.androidbox.domain.authentication.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val loginMutableState: MutableStateFlow<ResponseState<LoginUser>?> = MutableStateFlow(null)
    val loginState = loginMutableState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    fun onUsernameChanged(newUsername: String) {
        email = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
    }

    fun onPasswordVisibilityChanged() {
        isPasswordVisible = !isPasswordVisible
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginMutableState.value = loginUseCase.execute(email, password)
        }
    }
}