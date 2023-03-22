package me.androidbox.data.local.event

import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.mapper.DataToDomainMapper
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao,
    private val dataToDomainMapper: DataToDomainMapper<@JvmSuppressWildcards List<EventEntity>, @JvmSuppressWildcards List<Event>>
) : EventRepository {

    override suspend fun getEventsFromTimeStamp(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): ResponseState<List<Event>> {
        val result = checkResult<List<EventEntity>> {
            eventDao.getEventsFromTimeStamp(startTimeStamp, endTimeStamp)
        }

        val responseState = result.fold(
            onSuccess = { listOfEventEntity ->
                val listOfEvent = dataToDomainMapper(listOfEventEntity)

                ResponseState.Success(listOfEvent)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )

        return responseState
    }

    override suspend fun insertEvent(event: Event): ResponseState<Unit> {
        val eventEntity = EventEntity(
            id = event.id,
            title = event.title,
            description = event.description,
            startDateTime = event.startDateTime,
            endDateTime = event.endDateTime,
            remindAt = event.remindAt,
            eventCreatorId = event.eventCreatorId,
            isUserEventCreator = event.isUserEventCreator,
            attendees = event.attendees,
            photos = event.photos
        )
        val result = checkResult {
            eventDao.insertEvent(eventEntity)
        }

        val responseState = result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )

        return responseState
    }

    override suspend fun deleteEventById(id: String): ResponseState<Unit> {
        val result = checkResult {
            eventDao.deleteEventById(id)
        }

        val responseState = result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )

        return responseState
    }
}