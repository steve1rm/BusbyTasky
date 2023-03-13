package me.androidbox.data.remote.util

import kotlinx.coroutines.CancellationException

object CheckResult {
    suspend fun <T> checkResult(block: suspend () -> T): Result<T> {
        return try {
            return Result.success(block())
        }
        catch (exception: CancellationException) {
            throw exception
        }
        catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}