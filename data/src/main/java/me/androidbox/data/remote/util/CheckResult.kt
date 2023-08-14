package me.androidbox.data.remote.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CancellationException
import me.androidbox.data.remote.model.response.ErrorResponseDto
import org.json.JSONException
import retrofit2.HttpException

object CheckResult {
    suspend fun <T> checkResult(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        }
        catch (exception: CancellationException) {
            throw exception
        }
        catch (httpException: HttpException) {
            val responseBuffer = httpException.response()?.errorBody()?.source()?.buffer

            if (responseBuffer != null) {
                val buffer = responseBuffer.buffer

                val moshi = Moshi
                    .Builder().add(KotlinJsonAdapterFactory())
                    .build()
                    .adapter(ErrorResponseDto::class.java)

                try {
                    val errorResponseModel = moshi.fromJson(buffer)

                    if (errorResponseModel != null) {
                        Result.failure(NetworkHttpException(
                            errorMessage = errorResponseModel.message,
                            errorCode = httpException.code()
                        ))
                    } else {
                        Result.failure(httpException)
                    }
                }
                catch (jsonException: JSONException) {
                    Result.failure(NetworkHttpException(
                        errorMessage = "Failed to parse json response",
                        errorCode = httpException.code()
                    ))
                }
            }
            else {
                Result.failure(httpException)
            }
        }
        catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
