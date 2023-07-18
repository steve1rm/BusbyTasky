package me.androidbox.domain.event.usecase

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.authentication.ResponseState

interface VerifyVisitorEmailUseCase {
    fun execute(email: String): Flow<ResponseState<Attendee?>>
}