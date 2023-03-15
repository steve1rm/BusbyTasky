package me.androidbox.domain.authentication.usecase

fun interface SaveFullNameUseCase {
    fun execute(fullName: String)
}