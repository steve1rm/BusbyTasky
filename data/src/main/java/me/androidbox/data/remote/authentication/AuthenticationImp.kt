package me.androidbox.data.remote.authentication

import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.domain.authentication.model.LoginModel
import me.androidbox.domain.authentication.remote.Authentication
import javax.inject.Inject

class AuthenticationImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : Authentication {

    override suspend fun registerUser(fullName: String, email: String, password: String) {
        val registrationRequestDto = RegistrationRequestDto(
            fullName = fullName,
            email = email,
            password = password
        )

        authenticationService.register(registrationRequestDto)
    }

    override suspend fun loginUser(email: String, password: String): LoginModel {
        val loginRequestDto = LoginRequestDto(
            email = email,
            password = password
        )
        val loginDto = authenticationService.login(loginRequestDto)

        return LoginModel(
            token = loginDto.token,
            userId = loginDto.userId,
            fullName = loginDto.fullName
        )
    }
}