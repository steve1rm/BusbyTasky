package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.ResponseState

fun interface LogoutUseCase {
    suspend fun execute(): ResponseState<Unit>
}