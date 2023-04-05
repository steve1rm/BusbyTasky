package me.androidbox.data.local.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.androidbox.data.local.converter.AttendeeConverter
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.mapper.toEvent
import me.androidbox.data.mapper.toEventEntity
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao,
    private val attendeeConverter: AttendeeConverter
) : EventRepository {

    override fun getEventsFromTimeStamp(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): Flow<ResponseState<List<Event>>> {

        return eventDao.getEventsFromTimeStamp(startTimeStamp, endTimeStamp)
            .map { listOfEventEntity ->
                val listOfEvent = listOfEventEntity.map { eventEntity ->
                    eventEntity.toEvent(attendeeConverter)
                }

                ResponseState.Success(listOfEvent)
            }
            .catch { throwable ->
                ResponseState.Failure(throwable)
            }
    }

    override suspend fun insertEvent(event: Event): ResponseState<Unit> {
        val eventEntity = event.toEventEntity(attendeeConverter)

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