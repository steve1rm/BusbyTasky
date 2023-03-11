package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.NetworkResponseState

fun interface RegisterUseCase {
    suspend fun execute(fullName: String, email: String, password: String): NetworkResponseState<Unit>
}