package me.androidbox.data.remote.network

import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BusbyTaskyService {
    @POST(EndPoint.REGISTER)
    suspend fun register(
        @Body registrationRequestDto: RegistrationRequestDto)

    @POST(EndPoint.LOGIN)
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    )

    @GET(EndPoint.LOGOUT)
    suspend fun logout()

    @GET(EndPoint.AUTHENTICATE)
    suspend fun authenticate()
}