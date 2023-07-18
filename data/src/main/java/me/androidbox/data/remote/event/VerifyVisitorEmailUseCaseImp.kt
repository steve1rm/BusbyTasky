package me.androidbox.data.remote.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import me.androidbox.data.mapper.toAttendee
import me.androidbox.data.remote.model.response.AttendeeEmailVerifyResponseDto
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.event.usecase.VerifyVisitorEmailUseCase

class VerifyVisitorEmailUseCaseImp @Inject constructor(
    private val eventService: EventService
) : VerifyVisitorEmailUseCase {
    override fun execute(email: String): Flow<ResponseState<Attendee?>> {
        return flow {
            emit(ResponseState.Loading)

            val result = checkResult<AttendeeEmailVerifyResponseDto> {
                eventService.verifyAttendee(email)
            }

            result.fold(
                onSuccess = { value: AttendeeEmailVerifyResponseDto ->
                    if (value.doesUserExist) {
                        val attendee = value.attendee.toAttendee()
                        emit(ResponseState.Success(attendee))
                    } else {
                        emit(ResponseState.Success(null))
                    }
                },
                onFailure = { throwable ->
                    emit(ResponseState.Failure(throwable))
                }
            )
        }
    }
}
