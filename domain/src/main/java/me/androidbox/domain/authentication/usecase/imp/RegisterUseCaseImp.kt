package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.remote.AuthenticationRepository
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImp @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : RegisterUseCase {

    override suspend fun execute(fullName: String, email: String, password: String) {
        authenticationRepository.registerUser(
            fullName = fullName,
            email = email,
            password = password)
    }
}