package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.AuthenticateUserUseCase

class AuthenticateUserUseCaseImp @Inject constructor(
    private val authenticationRepository: AuthenticationRepository) :
    AuthenticateUserUseCase {
    override suspend fun execute(): ResponseState<Unit> {
        return authenticationRepository.authenticateUser()
    }
}