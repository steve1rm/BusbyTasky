package me.androidbox.data.local.agenda

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.mapper.*
import me.androidbox.domain.agenda.model.Agenda
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.repository.AgendaRemoteRepository
import java.time.ZoneId
import javax.inject.Inject

class AgendaLocalRepositoryImp @Inject constructor(
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
    private val agendaRemoteRepository: AgendaRemoteRepository
) : AgendaLocalRepository {

    override fun fetchAgenda(startTimeStamp: Long, endTimeStamp: Long): Flow<ResponseState<Agenda>> {
        return flow<ResponseState<Agenda>> {
            /* Fetch all agenda for the current day and time zone */
            val agenda = agendaRemoteRepository.fetchAgendaForDay(
                ZoneId.systemDefault(),
                startTimeStamp * 1000
            )

            /* Insert events tasks and reminders into the DB */
            supervisorScope {
                agenda.events.map { event ->
                    launch {
                        eventDao.insertEvent(event.toEventEntity())
                    }
                }.forEach { job -> job.join() }
            }

            supervisorScope {
                agenda.tasks.map { task ->
                    launch {
                        taskDao.insertTask(task.toTaskEntity())
                    }
                }.forEach { job -> job.join() }
            }

            supervisorScope {
                agenda.reminders.map { reminder ->
                    launch {
                        reminderDao.insertReminder(reminder.toReminderEntity())
                    }
                }.forEach { job -> job.join() }
            }

            /* Fetch all the events, tasks, and reminders from the DB and return to populate the agenda screen */
            val events = eventDao.getEventsFromTimeStamp(startTimeStamp, endTimeStamp)
                .map { listOfEventEntity ->
                    listOfEventEntity.map { eventEntity ->
                        eventEntity.toEvent()
                    }
                }

            val tasks = taskDao.getTasksFromTimeStamp(startTimeStamp, endTimeStamp)
                .map { listOfTaskEntity ->
                    listOfTaskEntity.map { taskEntity ->
                        taskEntity.toTask()
                    }
                }

            val reminders = reminderDao.getRemindersFromTimeStamp(startTimeStamp, endTimeStamp)
                .map { listOfReminderEntity ->
                    listOfReminderEntity.map { reminderEntity ->
                        reminderEntity.toReminder()
                    }
                }

            val fullAgenda = Agenda(
                events = events.single(),
                tasks = tasks.single(),
                reminders = reminders.single()
            )

            emit(ResponseState.Success(fullAgenda))
        }
            .catch { throwable ->
                emit(ResponseState.Failure(throwable))
            }
    }
}
