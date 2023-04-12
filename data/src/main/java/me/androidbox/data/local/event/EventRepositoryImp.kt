package me.androidbox.data.local.event

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.mapper.toEvent
import me.androidbox.data.mapper.toEventEntity
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.repository.AgendaRepository
import java.time.ZoneId
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
    private val agendaRepository: AgendaRepository
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

                val agenda = agendaRepository.fetchAgendaForDay(ZoneId.systemDefault(), System.currentTimeMillis())
                Log.d("EVENT_REPOSITORY", "EVENT_REPOSITORY ${agenda.events.count()}")

                ResponseState.Success(listOfEvent)
            }
            .catch { throwable ->
                ResponseState.Failure(throwable)
            }
    }

    override suspend fun insertEvent(event: Event): ResponseState<Unit> {
        val eventEntity = event.toEventEntity()

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