package me.androidbox.domain.authentication

sealed interface ResponseState<out T> {
    object Loading : ResponseState<Nothing>

    data class Success<T>(val data: T) : ResponseState<T>

    data class Failure(val error: Exception) : ResponseState<Nothing>
}
