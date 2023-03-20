package me.androidbox.data.local.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao
) : EventRepository {

    override fun getEventsFromTimeStamp(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): Flow<ResponseState<List<Event>>> {
        return flow<ResponseState<List<Event>>> {
            eventDao.getEventsFromTimeStamp(startTimeStamp, endTimeStamp).onEach { listOfEventEntity ->
                val listOfEvent = listOfEventEntity.map { eventEntity ->
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
                emit(ResponseState.Success(listOfEvent))
            }
        }.catch { throwable ->
            emit(ResponseState.Failure(throwable))
        }
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