package me.androidbox.presentation

sealed interface NetworkResponseState<out T> {
    object Idle : NetworkResponseState<Nothing>

    object Loading : NetworkResponseState<Nothing>

    data class Success<T>(val data: T) : NetworkResponseState<T>

    data class Failure<T: Exception>(val error: T) : NetworkResponseState<T>
}
