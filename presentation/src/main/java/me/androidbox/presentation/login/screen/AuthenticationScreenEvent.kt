package me.androidbox.presentation.login.screen

sealed interface AuthenticationScreenEvent {
    data class OnPasswordChanged(val password: String): AuthenticationScreenEvent
    data class OnEmailChanged(val email: String): AuthenticationScreenEvent
    object OnPasswordVisibilityChanged: AuthenticationScreenEvent
    data class OnUsernameChanged(val username: String): AuthenticationScreenEvent
    object OnAuthenticationUser: AuthenticationScreenEvent
    object OnRegisterUser: AuthenticationScreenEvent
    data class OnLoading(val isLoading: Boolean): AuthenticationScreenEvent
}
