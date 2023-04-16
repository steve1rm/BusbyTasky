package me.androidbox.data.worker_manager

import androidx.work.*
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.work_manager.SyncAgendaItems
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncAgendaItemsImp @Inject constructor(
    private val workManager: WorkManager,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao
) : SyncAgendaItems {

    override suspend fun sync() {

        /* Use eventDao to get the deleted items */
        val deletedEventIds = eventDao.getAllEventsBySyncType(SyncAgendaType.DELETE)
        val deletedTaskIds = taskDao.getAllTasksBySyncType(SyncAgendaType.DELETE)
        val deletedReminderIds = reminderDao.getAllRemindersBySyncType(SyncAgendaType.DELETE)

        val syncWorkerRequest = PeriodicWorkRequestBuilder<SyncAgendaItemsWorker>(
            repeatInterval = 15L,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .setInputData(workDataOf())
            .build()

        workManager.enqueueUniquePeriodicWork(
            "",
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkerRequest,
        )
    }
}