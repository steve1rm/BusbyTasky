package me.androidbox.domain.event.usecase

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Attendee

interface VerifyVisitorEmailUseCase {
    suspend fun execute(email: String): ResponseState<Attendee?>
}