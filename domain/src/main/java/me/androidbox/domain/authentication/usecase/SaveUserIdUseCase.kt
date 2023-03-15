package me.androidbox.domain.authentication.usecase

fun interface SaveUserIdUseCase {
    fun execute(userId: String)
}