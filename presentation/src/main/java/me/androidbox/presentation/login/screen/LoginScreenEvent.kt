package me.androidbox.presentation.login.screen

sealed interface LoginScreenEvent {
    data class OnPasswordChanged(val password: String): LoginScreenEvent
    data class OnEmailChanged(val email: String): LoginScreenEvent
    object OnPasswordVisibilityChanged: LoginScreenEvent
    object OnLoginUser: LoginScreenEvent
}
