package me.androidbox.data.local.event

import kotlinx.coroutines.flow.*
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao
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
                val listOfEntity = listOfEventEntity.map { eventEntity ->
                    Event(
                        id = eventEntity.id,
                        title = eventEntity.title,
                        description = eventEntity.description,
                        startDateTime = eventEntity.startDateTime,
                        endDateTime = eventEntity.endDateTime,
                        remindAt = eventEntity.remindAt,
                        host = eventEntity.host,
                        isUserEventCreator = eventEntity.isUserEventCreator,
                        attendees = eventEntity.attendees,
                        photos = eventEntity.photos
                    )
                }

                ResponseState.Success(listOfEntity)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )

        return responseState
    }

    override suspend fun insertEvent(event: Event) {
        val eventEntity = EventEntity(
            id = event.id,
            title = event.title,
            description = event.description,
            startDateTime = event.startDateTime,
            endDateTime = event.endDateTime,
            remindAt = event.remindAt,
            host = event.host,
            isUserEventCreator = event.isUserEventCreator,
            attendees = event.attendees,
            photos = event.photos
        )
        eventDao.insertEvent(eventEntity)
    }

    override suspend fun deleteEventById(id: String) {
        eventDao.deleteEventById(id)
    }
}