package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.RegisterUseCase

class RegisterUseCaseImp @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : RegisterUseCase {

    override suspend fun execute(fullName: String, email: String, password: String): ResponseState<Unit> {
        return authenticationRepository.registerUser(
            fullName = fullName,
            email = email,
            password = password)
    }
}