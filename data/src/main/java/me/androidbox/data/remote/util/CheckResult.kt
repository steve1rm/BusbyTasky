package me.androidbox.data.remote.util

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

object CheckResult {
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

}