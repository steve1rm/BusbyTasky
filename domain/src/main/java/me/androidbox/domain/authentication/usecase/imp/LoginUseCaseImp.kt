package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImp @Inject constructor(private val authenticationRepository: AuthenticationRepository): LoginUseCase {
    override suspend fun execute(email: String, password: String): ResponseState<Login> {
        return authenticationRepository.loginUser(email, password)
    }
}
