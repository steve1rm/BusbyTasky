package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.remote.Authentication
import me.androidbox.domain.authentication.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImp @Inject constructor(
    private val authentication: Authentication
) : RegisterUseCase {

    override suspend fun execute(fullName: String, email: String, password: String) {
        authentication.registerUser(
            fullName = fullName,
            email = email,
            password = password)
    }
}