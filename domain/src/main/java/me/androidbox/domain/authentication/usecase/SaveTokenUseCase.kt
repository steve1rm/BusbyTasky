package me.androidbox.domain.authentication.usecase

fun interface SaveTokenUseCase {
    suspend fun execute(token: String)
}