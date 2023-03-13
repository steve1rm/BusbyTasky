package me.androidbox.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.AuthenticateUserUseCase
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val authenticationMutableState: MutableStateFlow<ResponseState<Unit>?> = MutableStateFlow(null)
    val authenticationState = authenticationMutableState.asStateFlow()

    private val isCompletedMutableState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCompletedState = isCompletedMutableState.asStateFlow()

    fun authenticateUser() {
        viewModelScope.launch {
            authenticationMutableState.value = authenticateUserUseCase.execute()
            isCompletedMutableState.value = true
        }
    }
}