package me.androidbox.domain.authentication.usecase

fun interface SaveUserIdUseCase {
    suspend fun execute(userId: String)
}