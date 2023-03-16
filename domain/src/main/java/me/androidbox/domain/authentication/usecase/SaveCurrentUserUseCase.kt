package me.androidbox.domain.authentication.usecase

import me.androidbox.domain.authentication.model.LoginUser

fun interface SaveCurrentUserUseCase {
    fun execute(loginUser: LoginUser)
}