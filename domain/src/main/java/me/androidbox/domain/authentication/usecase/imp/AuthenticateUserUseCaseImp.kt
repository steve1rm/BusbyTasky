package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.NetworkResponseState
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.AuthenticateUserUseCase
import javax.inject.Inject

class AuthenticateUserUseCaseImp @Inject constructor(
    private val authenticationRepository: AuthenticationRepository)
    : AuthenticateUserUseCase {
    override suspend fun execute(): NetworkResponseState<Unit> {
        return authenticationRepository.authenticateUser()
    }
}