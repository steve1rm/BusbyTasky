package me.androidbox.domain.authentication.usecase

fun interface DeleteCurrentUserUseCase {
    suspend fun execute()
}