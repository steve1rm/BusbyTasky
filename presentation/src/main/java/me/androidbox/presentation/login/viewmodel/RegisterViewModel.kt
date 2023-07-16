package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import me.androidbox.domain.login.usecase.ValidateEmailUseCase
import me.androidbox.domain.login.usecase.ValidatePasswordUseCase
import me.androidbox.presentation.login.screen.AuthenticationScreenEvent
import me.androidbox.presentation.login.screen.AuthenticationScreenState

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val registerScreenMutableState = MutableStateFlow(AuthenticationScreenState<Unit>())
    val registerScreenState = registerScreenMutableState.asStateFlow()

    private val email = savedStateHandle.getStateFlow("email", "")
    private val password = savedStateHandle.getStateFlow("password", "")

    val validateCredentials = combine(email, password) { email, password ->
        AuthenticationScreenState<AuthenticatedUser>(
            email = email,
            password = password,
            isValidEmail = validateEmailUseCase.execute(email),
            isValidPassword = validatePasswordUseCase.execute(password)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AuthenticationScreenState())

    fun onRegistrationEvent(authenticationScreenEvent: AuthenticationScreenEvent) {
        when (authenticationScreenEvent) {
            is AuthenticationScreenEvent.OnEmailChanged -> {
                savedStateHandle["email"] = authenticationScreenEvent.email
            }
            is AuthenticationScreenEvent.OnUsernameChanged -> {
                registerScreenMutableState.value = registerScreenState.value.copy(
                    username = authenticationScreenEvent.username
                )
            }
            is AuthenticationScreenEvent.OnPasswordChanged -> {
                savedStateHandle["password"] = authenticationScreenEvent.password
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

            /** TODO Show loading */
            is AuthenticationScreenEvent.OnLoading -> Unit
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