package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.ResponseState

fun interface RegisterUseCase {
    suspend fun execute(fullName: String, email: String, password: String): ResponseState<Unit>
}