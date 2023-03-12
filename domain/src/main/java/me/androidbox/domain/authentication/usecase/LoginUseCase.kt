package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.NetworkResponseState
import me.androidbox.domain.authentication.model.Login

fun interface LoginUseCase {
    suspend fun execute(email: String, password: String): NetworkResponseState<Login>
}