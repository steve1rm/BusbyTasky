package me.androidbox.data.remote.authentication

import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.model.response.LoginDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.remote.util.NetworkHttpException
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService
) : AuthenticationRepository {

    override suspend fun registerUser(fullName: String, email: String, password: String): ResponseState<Unit> {
        val registrationRequestDto = RegistrationRequestDto(
            fullName = fullName,
            email = email,
            password = password
        )

        val result = checkResult<Unit> {
            authenticationService.register(registrationRequestDto)
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                if(throwable is NetworkHttpException) {
                    ResponseState.Failure(throwable.errorMessage, throwable.errorCode)
                }
                else {
                    ResponseState.Failure(throwable.message)
                }
            }
        )
    }

    override suspend fun loginUser(email: String, password: String): ResponseState<AuthenticatedUser> {
        val loginRequestDto = LoginRequestDto(
            email = email,
            password = password
        )

        val result = checkResult<LoginDto> {
            authenticationService.login(loginRequestDto)
        }

        return result.fold(
            onSuccess = { loginDto ->
                val login = AuthenticatedUser(
                    token = loginDto.token,
                    userId = loginDto.userId,
                    fullName = loginDto.fullName)

                ResponseState.Success(login)
            },
            onFailure = { throwable: Throwable ->
                if(throwable is NetworkHttpException) {
                    ResponseState.Failure(throwable.errorMessage, throwable.errorCode)
                }
                else {
                    ResponseState.Failure(throwable.message)
                }
            }
        )
    }

    override suspend fun authenticateUser(): ResponseState<Unit> {
        val result = checkResult<Unit> {
            authenticationService.authenticate()
        }

        return if (result.isSuccess) {
            ResponseState.Success(Unit)
        } else {
            ResponseState.Failure(result.toString())
        }
    }

    override suspend fun logout(): ResponseState<Unit> {
        val result = checkResult<Unit> {
            authenticationService.logout()
        }

        return if (result.isSuccess) {
            ResponseState.Success(Unit)
        } else {
            ResponseState.Failure(result.toString())
        }
    }
}