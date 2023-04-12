package me.androidbox.domain.event.usecase

import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.authentication.ResponseState

interface VerifyVisitorEmailUseCase {
    suspend fun execute(email: String): ResponseState<Attendee?>
}