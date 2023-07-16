package me.androidbox.data.remote.network.authentication

import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.model.response.LoginDto
import me.androidbox.data.remote.network.EndPointConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    @POST(EndPointConstant.REGISTER)
    suspend fun register(
        @Body registrationRequestDto: RegistrationRequestDto)

    @POST(EndPointConstant.LOGIN)
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    ): LoginDto

    @GET(EndPointConstant.LOGOUT)
    suspend fun logout()

    @GET(EndPointConstant.AUTHENTICATE)
    suspend fun authenticate()
}