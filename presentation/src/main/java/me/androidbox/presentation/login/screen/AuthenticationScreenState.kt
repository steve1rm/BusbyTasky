package me.androidbox.presentation.login.screen

import me.androidbox.domain.authentication.ResponseState

data class AuthenticationScreenState<T>(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val username: String = "",
    val responseState: ResponseState<T>? = null,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false
)
