package me.androidbox.data.remote.authentication

import kotlinx.coroutines.CancellationException
import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.domain.authentication.NetworkResponseState
import me.androidbox.domain.authentication.model.LoginModel
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {

    override suspend fun registerUser(fullName: String, email: String, password: String): NetworkResponseState<Unit> {
        val registrationRequestDto = RegistrationRequestDto(
            fullName = fullName,
            email = email,
            password = password
        )

        return try {
            authenticationService.register(registrationRequestDto)
            NetworkResponseState.Success(Unit)
        }
        catch(httpException: HttpException) {
             NetworkResponseState.Failure(httpException)
        }
        catch (exception: Exception) {
            if(exception is CancellationException) {
                throw Exception()
            }
            NetworkResponseState.Failure(exception)
        }
    }

    override suspend fun loginUser(email: String, password: String): NetworkResponseState<LoginModel> {
        val loginRequestDto = LoginRequestDto(
            email = email,
            password = password
        )

        return try {
            val loginDto = authenticationService.login(loginRequestDto)

            val loginModel = LoginModel(
                token = loginDto.token,
                userId = loginDto.userId,
                fullName = loginDto.fullName
            )

            return NetworkResponseState.Success(loginModel)
        }
        catch(httpException: HttpException) {
            NetworkResponseState.Failure(httpException)
        }
        catch (exception: Exception) {
            if(exception is CancellationException) {
                throw Exception()
            }
            NetworkResponseState.Failure(exception)
        }
    }
}