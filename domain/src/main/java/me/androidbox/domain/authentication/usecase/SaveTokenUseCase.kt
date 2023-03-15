package me.androidbox.domain.authentication.usecase

fun interface SaveTokenUseCase {
    fun execute(token: String)
}