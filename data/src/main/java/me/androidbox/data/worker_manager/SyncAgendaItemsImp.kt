package me.androidbox.data.worker_manager

import androidx.work.*
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.work_manager.SyncAgendaItems
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncAgendaItemsImp @Inject constructor(
    private val workManager: WorkManager,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao
) : SyncAgendaItems {

    companion object {
        const val DELETED_EVENT_IDS = "deletedEventIds"
        const val DELETED_TASK_IDS = "deletedTaskIds"
        const val DELETED_REMINDER_IDS = "deletedReminderIds"
        const val THIRTY_MINUTES = 30L
        const val SYNC_DELETED_AGENDA_IDS = "syncDeletedAgendaIds"
    }

    override suspend fun sync(): UUID {

        /* Use eventDao to get the deleted items */
        val deletedEventIds = eventDao.getAllEventsBySyncType(SyncAgendaType.DELETE)
        val deletedTaskIds = taskDao.getAllTasksBySyncType(SyncAgendaType.DELETE)
        val deletedReminderIds = reminderDao.getAllRemindersBySyncType(SyncAgendaType.DELETE)

        val deletedAgendaItemsIds = workDataOf(
            DELETED_EVENT_IDS to deletedEventIds.toTypedArray(),
            DELETED_TASK_IDS to deletedTaskIds.toTypedArray(),
            DELETED_REMINDER_IDS to deletedReminderIds.toTypedArray()
        )

        val syncWorkerRequest = PeriodicWorkRequestBuilder<SyncAgendaItemsWorker>(
            repeatInterval = THIRTY_MINUTES,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .setInputData(deletedAgendaItemsIds)
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_DELETED_AGENDA_IDS,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkerRequest,
        )

        return syncWorkerRequest.id
    }
}