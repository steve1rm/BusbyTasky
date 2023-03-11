package me.androidbox.domain.authentication.usecase

fun interface AuthenticateUserUseCase {
    suspend fun execute()
}