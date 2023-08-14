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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.domain.authentication.usecase.SaveCurrentUserUseCase
import me.androidbox.domain.login.usecase.ValidateEmailUseCase
import me.androidbox.presentation.login.screen.AuthenticationScreenEvent
import me.androidbox.presentation.login.screen.AuthenticationScreenState
import retrofit2.HttpException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val saveCurrentUserUseCase: SaveCurrentUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }

    private val _authenticateUserState = MutableStateFlow(AuthenticationScreenState<AuthenticatedUser>())
    val authenticateUserState = _authenticateUserState.asStateFlow()

    private val email = savedStateHandle.getStateFlow(EMAIL, "")
    private val password = savedStateHandle.getStateFlow(PASSWORD, "")

    val validateCredentialsState = combine(email, password) { email, password ->
        AuthenticationScreenState<AuthenticatedUser>(
            email = email,
            password = password,
            isValidEmail = validateEmailUseCase.execute(email),
            isValidPassword = validateEmailUseCase.execute(password)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AuthenticationScreenState<AuthenticatedUser>())

    fun onLoginEvent(authenticationScreenEvent: AuthenticationScreenEvent) {
        when (authenticationScreenEvent) {
            is AuthenticationScreenEvent.OnEmailChanged -> {
                savedStateHandle[EMAIL] = authenticationScreenEvent.email
            }
            is AuthenticationScreenEvent.OnPasswordChanged -> {
                savedStateHandle[PASSWORD] = authenticationScreenEvent.password
            }
            is AuthenticationScreenEvent.OnPasswordVisibilityChanged -> {
                _authenticateUserState.value = authenticateUserState.value.copy(
                    isPasswordVisible = !authenticateUserState.value.isPasswordVisible
                )
            }
            AuthenticationScreenEvent.OnAuthenticationUser -> {
                _authenticateUserState.update { _ ->
                    authenticateUserState.value.copy(
                        responseState = ResponseState.Loading
                    )
                }
                loginUser(email.value, password.value)
            }
            is AuthenticationScreenEvent.OnIsLoading -> {
                _authenticateUserState.update { _ ->
                    authenticateUserState.value.copy(
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

            /* Save details of the current user that has logged in */
            if (loginResponseState is ResponseState.Success) {
                saveCurrentUserDetails(loginResponseState.data)
            }
            if(loginResponseState is ResponseState.Failure) {
         //       val error = loginResponseState.error as HttpException
         //       error.response()?.errorBody()
       //         error.response()?.errorBody().toString()

            }
            _authenticateUserState.value = _authenticateUserState.value.copy(
                responseState = loginResponseState)
        }
    }

    private fun saveCurrentUserDetails(authenticatedUser: AuthenticatedUser) {
        saveCurrentUserUseCase.execute(authenticatedUser)
    }
}