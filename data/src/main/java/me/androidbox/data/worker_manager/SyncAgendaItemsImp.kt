package me.androidbox.data.worker_manager

import androidx.work.*
import me.androidbox.data.local.dao.EventDao
import me.androidbox.domain.work_manager.SyncAgendaItems
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncAgendaItemsImp @Inject constructor(
    private val workManager: WorkManager,
    private val eventDao: EventDao
) : SyncAgendaItems {

    override suspend fun sync() {

        /* Use eventDao to get the deleted items */

        val syncWorkerRequest = PeriodicWorkRequestBuilder<SyncAgendaItemsWorker>(repeatInterval = Duration.ofMinutes(15L))
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