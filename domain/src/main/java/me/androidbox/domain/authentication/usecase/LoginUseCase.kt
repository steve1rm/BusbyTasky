package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login

fun interface LoginUseCase {
    suspend fun execute(email: String, password: String): ResponseState<Login>
}