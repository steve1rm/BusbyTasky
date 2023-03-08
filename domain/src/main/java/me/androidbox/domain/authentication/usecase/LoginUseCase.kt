package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.model.LoginModel

fun interface LoginUseCase {
    suspend fun execute(email: String, password: String): LoginModel
}