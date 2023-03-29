package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.ResponseState

fun interface AuthenticateUserUseCase {
    suspend fun execute(): ResponseState<Unit>
}