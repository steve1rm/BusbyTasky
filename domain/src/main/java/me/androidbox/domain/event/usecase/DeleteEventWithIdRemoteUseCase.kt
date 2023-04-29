package me.androidbox.domain.event.usecase

import me.androidbox.domain.authentication.ResponseState

interface DeleteEventWithIdRemoteUseCase {
    suspend fun execute(eventId: String): ResponseState<Unit>
}