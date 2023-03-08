package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.model.LoginModel
import me.androidbox.domain.authentication.remote.Authentication
import me.androidbox.domain.authentication.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImp @Inject constructor(private val authentication: Authentication): LoginUseCase {
    override suspend fun execute(email: String, password: String): LoginModel {
        return authentication.loginUser(email, password)
    }
}
