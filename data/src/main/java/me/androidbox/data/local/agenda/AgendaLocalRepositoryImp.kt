package me.androidbox.data.local.agenda

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.mapper.*
import me.androidbox.domain.agenda.model.Agenda
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.Reminder
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.constant.SyncAgendaType
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

            /* Fetch all the events, tasks, and reminders from the DB and return to populate the agenda screen */
            val events = fetchEventsFromLocal(startTimeStamp, endTimeStamp)
            val tasks = fetchTasksFromLocal(startTimeStamp, endTimeStamp)
            val reminders = fetchRemindersFromLocal(startTimeStamp, endTimeStamp)

            /* Emit after fetching from the DB */
            emit(ResponseState.Success(Agenda(
                events = events,
                tasks = tasks,
                reminders = reminders
            )))

            /* Fetch all agenda for the current day and time zone */
            val agenda = agendaRemoteRepository.fetchAgendaForDay(
                ZoneId.systemDefault(),
                startTimeStamp
            )

            /* Insert events tasks and reminders into the DB */
            insertAllAgendaItems(agenda)

            /* Fetch all the events, tasks, and reminders from the DB and return to populate the agenda screen */
            val updatedEvents = fetchEventsFromLocal(startTimeStamp, endTimeStamp)
            val updatedTasks = fetchTasksFromLocal(startTimeStamp, endTimeStamp)
            val updatedReminders = fetchRemindersFromLocal(startTimeStamp, endTimeStamp)

            /* Emit after fetching from the DB and insertion from the Endpoint */
            emit(ResponseState.Success(Agenda(
                events = updatedEvents,
                tasks = updatedTasks,
                reminders = updatedReminders
            )))
        }
            .catch { throwable ->
                emit(ResponseState.Failure(throwable))
            }
    }

    override suspend fun deleteEventSyncType(syncAgendaType: SyncAgendaType) {
        eventDao.deleteSyncEventsBySyncType(syncAgendaType)
    }

    private suspend fun insertAllAgendaItems(agenda: Agenda) {
        supervisorScope {
            val eventJobs = agenda.events.map { event ->
                launch {
                    eventDao.insertEvent(event.toEventEntity())
                }
            }

            val taskJobs = agenda.tasks.map { task ->
                launch {
                    taskDao.insertTask(task.toTaskEntity())
                }
            }

            val reminderJobs = agenda.reminders.map { reminder ->
                launch {
                    reminderDao.insertReminder(reminder.toReminderEntity())
                }
            }

            (eventJobs + taskJobs + reminderJobs).joinAll()
        }
    }

    private suspend fun fetchRemindersFromLocal(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): List<Reminder> {
        val reminders =
            reminderDao.getRemindersFromTimeStampFullAgenda(startTimeStamp, endTimeStamp)
                .map { reminderEntity ->
                    reminderEntity.toReminder()
                }
        return reminders
    }

    private suspend fun fetchTasksFromLocal(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): List<Task> {
        val tasks = taskDao.getTasksFromTimeStampFullAgenda(startTimeStamp, endTimeStamp)
            .map { taskEntity ->
                taskEntity.toTask()
            }
        return tasks
    }

    private suspend fun fetchEventsFromLocal(
        startTimeStamp: Long,
        endTimeStamp: Long
    ): List<Event> {
        val events = eventDao.getEventsFromTimeStampFullAgenda(startTimeStamp, endTimeStamp)
            .map { eventEntity ->
                eventEntity.toEvent()
            }
        return events
    }
}
