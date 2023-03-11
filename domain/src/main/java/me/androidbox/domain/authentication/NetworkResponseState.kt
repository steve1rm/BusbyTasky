package me.androidbox.domain.authentication

sealed interface NetworkResponseState<out T> {
    object Loading : NetworkResponseState<Nothing>

    data class Success<T>(val data: T) : NetworkResponseState<T>

    data class Failure(val error: Exception) : NetworkResponseState<Nothing>
}
