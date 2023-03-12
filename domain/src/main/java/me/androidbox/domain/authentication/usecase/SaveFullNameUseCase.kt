package me.androidbox.domain.authentication.usecase

fun interface SaveFullNameUseCase {
    suspend fun execute(fullName: String)
}