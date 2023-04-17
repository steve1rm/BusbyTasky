package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.remote.model.request.SyncAgendaDto
import me.androidbox.data.remote.network.agenda.AgendaService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT
import me.androidbox.domain.constant.SyncAgendaType

@HiltWorker
class SyncAgendaItemsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
    private val agendaService: AgendaService
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val deletedEventIds: Deferred<List<String>>
        val deletedTaskIds: Deferred<List<String>>
        val deletedReminderIds: Deferred<List<String>>

        supervisorScope {
            deletedEventIds = async {
                eventDao.getAllEventsBySyncType(SyncAgendaType.DELETE)
            }

            deletedTaskIds = async {
                taskDao.getAllTasksBySyncType(SyncAgendaType.DELETE)
            }

            deletedReminderIds = async {
                reminderDao.getAllRemindersBySyncType(SyncAgendaType.DELETE)
            }
        }

        val responseResult = checkResult {
            val syncAgendaDto = SyncAgendaDto(
                deletedEventIds = deletedEventIds.await().toList(),
                deletedTaskIds = deletedTaskIds.await().toList(),
                deletedReminderIds = deletedReminderIds.await().toList())
            agendaService.syncAgenda(syncAgendaDto)
        }

        if(runAttemptCount > RETRY_COUNT) {
            Result.failure()
        }

        val result = responseResult.fold(
            onSuccess = {
                deleteAllDeletedSyncAgendaItems()
                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )

        return result
    }

    private suspend fun deleteAllDeletedSyncAgendaItems() {
        withContext(Dispatchers.IO) {
            eventDao.deleteSyncEventsBySyncType(SyncAgendaType.DELETE)
            taskDao.deleteSyncTasksBySyncType(SyncAgendaType.DELETE)
            reminderDao.deleteSyncRemindersBySyncType(SyncAgendaType.DELETE)
        }
    }
}