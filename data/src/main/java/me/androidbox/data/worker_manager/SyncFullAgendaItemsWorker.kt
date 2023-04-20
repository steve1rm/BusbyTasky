package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.local.entity.ReminderEntity
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.mapper.toAttendee
import me.androidbox.data.mapper.toEvent
import me.androidbox.data.mapper.toEventEntity
import me.androidbox.data.mapper.toReminder
import me.androidbox.data.mapper.toReminderEntity
import me.androidbox.data.mapper.toTask
import me.androidbox.data.mapper.toTaskEntity
import me.androidbox.data.remote.model.response.FullAgendaDto
import me.androidbox.data.remote.network.agenda.AgendaService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT

@HiltWorker
class SyncFullAgendaItemsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val agendaService: AgendaService,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val responseResult = checkResult {
            agendaService.fullAgenda()
        }

        if(runAttemptCount > RETRY_COUNT) {
            Result.failure()
        }

        val result = responseResult.fold(
            onSuccess = { fullAgendaDto ->
                insertAllAgendaItems(fullAgendaDto, eventDao, taskDao, reminderDao)
                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )

        return result
    }
}

private suspend fun insertAllAgendaItems(fullAgendaDto: FullAgendaDto, eventDao: EventDao, taskDao: TaskDao, reminderDao: ReminderDao) {
    supervisorScope {
        val eventJob = launch {
            fullAgendaDto.events.mapAsync { eventDto ->
                val eventEntity = eventDto.toEventEntity()
                eventDao.insertEvent(eventEntity)
            }
        }

        val taskJob = launch {
            fullAgendaDto.tasks.mapAsync { taskDto ->
                val taskEntity = taskDto.toTaskEntity()
                taskDao.insertTask(taskEntity)
            }
        }

        val reminderJob = launch {
            fullAgendaDto.reminders.mapAsync { reminderDto ->
                val reminderEntity = reminderDto.toReminderEntity()
                reminderDao.insertReminder(reminderEntity)
            }
        }

        eventJob.join()
        taskJob.join()
        reminderJob.join()
    }
}

suspend fun <T, R> List<T>.mapAsync(transformation: suspend (T) -> R): List<R> {
    return coroutineScope {
        this@mapAsync.map { item ->
            async {
                transformation(item)
            }
        }.awaitAll()
    }
}
