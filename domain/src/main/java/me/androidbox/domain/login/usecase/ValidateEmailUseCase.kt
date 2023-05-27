package me.androidbox.domain.login.usecase

fun interface ValidateEmailUseCase {
    fun execute(email: String): Boolean
}
