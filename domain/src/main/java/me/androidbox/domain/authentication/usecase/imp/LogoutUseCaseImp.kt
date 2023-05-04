package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.usecase.AuthenticateUserUseCase
import me.androidbox.domain.authentication.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutUseCaseImp @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : LogoutUseCase {
    override suspend fun execute(): ResponseState<Unit> {
        return authenticateUserUseCase.execute()
    }
}