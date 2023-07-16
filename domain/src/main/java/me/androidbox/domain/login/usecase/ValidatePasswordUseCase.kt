package me.androidbox.domain.login.usecase

fun interface ValidatePasswordUseCase {
    fun execute(password: String): Boolean
}