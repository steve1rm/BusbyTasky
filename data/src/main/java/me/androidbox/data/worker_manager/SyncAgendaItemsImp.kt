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
) : SyncAgendaItems {

    companion object {
        const val THIRTY_MINUTES = 30L
        const val SYNC_DELETED_AGENDA_IDS = "syncDeletedAgendaIds"
    }

    override suspend fun sync(): UUID {

        val syncWorkerRequest = PeriodicWorkRequestBuilder<SyncAgendaItemsWorker>(
            repeatInterval = THIRTY_MINUTES,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_DELETED_AGENDA_IDS,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkerRequest,
        )

        return syncWorkerRequest.id
    }
}