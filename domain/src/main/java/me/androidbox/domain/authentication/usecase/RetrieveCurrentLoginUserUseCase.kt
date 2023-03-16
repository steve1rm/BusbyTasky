package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.model.AuthenticatedUser

fun interface RetrieveCurrentLoginUserUseCase {
    fun execute(): AuthenticatedUser?
}