package me.androidbox.presentation.login.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.model.LoginModel
import me.androidbox.domain.authentication.usecase.LoginUseCase
import me.androidbox.presentation.NetworkResponseState
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val loginMutableState = MutableStateFlow<NetworkResponseState<LoginModel>>(NetworkResponseState.Idle)
    val loginState = loginMutableState.asStateFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isPasswordVisible by mutableStateOf(false)
        private set

    fun onUsernameChanged(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
    }

    fun onPasswordVisibilityChanged() {
        isPasswordVisible = !isPasswordVisible
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                loginMutableState.value = NetworkResponseState.Loading

                val loginModel = loginUseCase.execute(email, password)
                loginMutableState.value = NetworkResponseState.Success(loginModel)
            }
            catch (httpException: HttpException) {
                loginMutableState.value = NetworkResponseState.Failure(httpException)
            }
            catch (exception: Exception) {
                if(exception is CancellationException) {
                    throw Exception(exception.message)
                }
                loginMutableState.value = NetworkResponseState.Failure(exception)
            }
        }
    }
}