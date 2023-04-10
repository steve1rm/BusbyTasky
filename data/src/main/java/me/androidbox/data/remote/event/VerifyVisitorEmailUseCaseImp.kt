package me.androidbox.data.remote.event

import me.androidbox.data.remote.model.response.AttendeeEmailVerifyResponseDto
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.event.usecase.VerifyVisitorEmailUseCase
import javax.inject.Inject

class VerifyVisitorEmailUseCaseImp @Inject constructor(
    private val eventService: EventService
) : VerifyVisitorEmailUseCase {
    override suspend fun execute(email: String): ResponseState<Boolean> {

        val result = checkResult<AttendeeEmailVerifyResponseDto> {
            eventService.verifyAttendee(email)
        }

        return result.fold(
            onSuccess = { value: AttendeeEmailVerifyResponseDto ->
                ResponseState.Success(value.doesUserExist)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )
    }
}
