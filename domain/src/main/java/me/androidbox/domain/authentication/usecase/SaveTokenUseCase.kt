package me.androidbox.domain.authentication.usecase

interface SaveTokenUseCase {
    suspend fun execute(key: String, token: String)
}