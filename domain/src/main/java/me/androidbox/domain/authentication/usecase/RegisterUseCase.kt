package me.androidbox.domain.authentication.usecase

fun interface RegisterUseCase {
    suspend fun execute(fullName: String, email: String, password: String)
}