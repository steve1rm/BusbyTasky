package me.androidbox.data.remote.event

import javax.inject.Inject
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.event.usecase.DeleteEventWithIdRemoteUseCase

class DeleteEventWithIdRemoteUseCaseImp @Inject constructor(
    private val eventService: EventService
) : DeleteEventWithIdRemoteUseCase {

    override suspend fun execute(eventId: String): ResponseState<Unit> {
        val result = checkResult {
            eventService.deleteEventWithId(eventId)
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable.message.orEmpty())
            }
        )
    }
}