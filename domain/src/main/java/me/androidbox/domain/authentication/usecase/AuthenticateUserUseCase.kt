package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.NetworkResponseState

fun interface AuthenticateUserUseCase {
    suspend fun execute(): NetworkResponseState<Unit>
}