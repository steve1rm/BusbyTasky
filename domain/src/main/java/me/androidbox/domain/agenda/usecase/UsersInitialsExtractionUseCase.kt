package me.androidbox.domain.agenda.usecase

fun interface UsersInitialsExtractionUseCase {
    fun execute(fullName: String): String
}