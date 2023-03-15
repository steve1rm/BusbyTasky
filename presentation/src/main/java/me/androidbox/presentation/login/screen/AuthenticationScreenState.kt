package me.androidbox.presentation.login.screen

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login

data class AuthenticationScreenState<T>(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean,
    val username: String,
    val responseState: ResponseState<T>?
)
