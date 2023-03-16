package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.model.AuthenticatedUser

fun interface SaveCurrentUserUseCase {
    fun execute(authenticatedUser: AuthenticatedUser)
}