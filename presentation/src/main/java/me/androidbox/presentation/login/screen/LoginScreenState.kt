package me.androidbox.presentation.login.screen

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login

data class LoginScreenState(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean,
    val username: String,
    val loginResponseState: ResponseState<Login>?,
    val registrationResponseState: ResponseState<Unit>?
)
