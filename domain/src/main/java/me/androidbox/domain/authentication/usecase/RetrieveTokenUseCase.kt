package me.androidbox.domain.authentication.usecase

interface RetrieveTokenUseCase {
    suspend fun execute(key: String): String?
}