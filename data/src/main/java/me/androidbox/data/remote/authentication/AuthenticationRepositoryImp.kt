package me.androidbox.data.remote.authentication

import kotlinx.coroutines.CancellationException
import me.androidbox.data.remote.model.request.LoginRequestDto
import me.androidbox.data.remote.model.request.RegistrationRequestDto
import me.androidbox.data.remote.model.response.LoginDto
import me.androidbox.data.remote.network.authentication.AuthenticationService
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.LoginUser
import me.androidbox.domain.authentication.remote.AuthenticationRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {

    /* TODO Move this to a utils file to be used in other classes */
    suspend fun <T> checkResult(block: suspend () -> T): Result<T> {
        return try {
            return Result.success(block())
        }
        catch (httpException: HttpException) {
            Result.failure(httpException)
        }
        catch (exception: Exception) {
            if (exception is CancellationException) {
                throw exception
            }
            Result.failure(exception)
        }
    }

    override suspend fun registerUser(fullName: String, email: String, password: String): ResponseState<Unit> {
        val registrationRequestDto = RegistrationRequestDto(
            fullName = fullName,
            email = email,
            password = password
        )

        val result = checkResult<Unit> {
            authenticationService.register(registrationRequestDto)
        }

        /* TODO Is this the best way to extract the value from the Result<T> */
        val unit = result.getOrNull()
        return if(unit!= null) {
            ResponseState.Success(unit)
        } else {
            ResponseState.Failure((result.exceptionOrNull() ?: Exception()) as Exception)
        }
    }

    override suspend fun loginUser(email: String, password: String): ResponseState<LoginUser> {
        val loginRequestDto = LoginRequestDto(
            email = email,
            password = password
        )

        val result = checkResult<LoginDto> {
            authenticationService.login(loginRequestDto)
        }

        /* TODO Is this the best way to extract the value from the Result<T> */
        val loginDto = result.getOrNull()
        return if(loginDto != null) {
            val login = LoginUser(
                token = loginDto.token,
                userId = loginDto.userId,
                fullName = loginDto.fullName
            )
            ResponseState.Success(login)
        } else {
            ResponseState.Failure((result.exceptionOrNull() ?: Exception()) as Exception)
        }
    }
}