package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.LoginUseCase

class LoginUseCaseImp @Inject constructor(private val authenticationRepository: AuthenticationRepository) : LoginUseCase {
    override suspend fun execute(email: String, password: String): ResponseState<AuthenticatedUser> {
        return authenticationRepository.loginUser(email, password)
    }
}
