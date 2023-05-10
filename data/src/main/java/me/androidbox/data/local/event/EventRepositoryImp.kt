package me.androidbox.data.local.event

import androidx.room.Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.local.entity.EventSyncEntity
import me.androidbox.data.mapper.toEvent
import me.androidbox.data.mapper.toEventEntity
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.constant.SyncAgendaType
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao
) : EventRepository {

    override fun getEventsFromTimeStamp(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): Flow<ResponseState<List<Event>>> {

        return eventDao.getEventsFromTimeStamp(startTimeStamp, endTimeStamp)
            .map { listOfEventEntity ->
                val listOfEvent = listOfEventEntity.map { eventEntity ->
                    eventEntity.toEvent()
                }

                ResponseState.Success(listOfEvent)
            }
            .catch { throwable ->
                ResponseState.Failure(throwable)
            }
    }

    override suspend fun getEventById(id: String): ResponseState<Event> {
        return ResponseState.Success(eventDao.getEventById(id).toEvent())
    }

    override suspend fun insertEvent(event: Event): ResponseState<Unit> {
        val eventEntity = event.toEventEntity()

        val result = checkResult {
            eventDao.insertEvent(eventEntity)
            eventDao.insertSyncEvent(EventSyncEntity(event.id, SyncAgendaType.CREATE))
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

    override suspend fun insertSyncEvent(eventId: String, syncAgendaType: SyncAgendaType): ResponseState<Unit> {
        val result = checkResult {
            eventDao.insertSyncEvent(EventSyncEntity(eventId, SyncAgendaType.DELETE))
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