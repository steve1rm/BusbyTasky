package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.AuthenticateUserUseCase

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val authenticationMutableState: MutableStateFlow<ResponseState<Unit>> = MutableStateFlow(ResponseState.Loading)
    val authenticationState = authenticationMutableState.asStateFlow()

    init {
        authenticateUser()
    }

    private fun authenticateUser() {
        viewModelScope.launch {
            authenticationMutableState.value = authenticateUserUseCase.execute()
        }
    }
}