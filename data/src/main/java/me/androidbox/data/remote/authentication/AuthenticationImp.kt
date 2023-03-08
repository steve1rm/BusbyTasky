package me.androidbox.data.remote.authentication

import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.domain.authentication.remote.Authentication
import javax.inject.Inject

class AuthenticationImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : Authentication {

    override suspend fun registerUser(fullName: String, email: String, password: String) {
        /* MOCK DATA */
        val registrationRequestDto = RegistrationRequestDto(
            fullName = "JoeBlogs",
            email = "joe1@gmail.com",
            password = "Password1"
        )

        authenticationService.register(
            registrationRequestDto
        )
    }
}