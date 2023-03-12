package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.model.LoginUser

fun interface RetrieveCurrentLoginUserUseCase {
    suspend fun execute(): LoginUser?
}