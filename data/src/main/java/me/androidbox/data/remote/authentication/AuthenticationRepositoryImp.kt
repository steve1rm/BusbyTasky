package me.androidbox.data.remote.authentication

import kotlinx.coroutines.CancellationException
import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {

    override suspend fun registerUser(fullName: String, email: String, password: String): ResponseState<Unit> {
        val registrationRequestDto = RegistrationRequestDto(
            fullName = fullName,
            email = email,
            password = password
        )

        return try {
            authenticationService.register(registrationRequestDto)
            ResponseState.Success(Unit)
        }
        catch(httpException: HttpException) {
             ResponseState.Failure(httpException)
        }
        catch (exception: Exception) {
            if(exception is CancellationException) {
                throw exception
            }
            ResponseState.Failure(exception)
        }
    }

    override suspend fun loginUser(email: String, password: String): ResponseState<Login> {
        val loginRequestDto = LoginRequestDto(
            email = email,
            password = password
        )

        return try {
            val loginDto = authenticationService.login(loginRequestDto)

            val login = Login(
                token = loginDto.token,
                userId = loginDto.userId,
                fullName = loginDto.fullName
            )

            return ResponseState.Success(login)
        }
        catch(httpException: HttpException) {
            ResponseState.Failure(httpException)
        }
        catch (exception: Exception) {
            if(exception is CancellationException) {
                throw exception
            }
            ResponseState.Failure(exception)
        }
    }
}