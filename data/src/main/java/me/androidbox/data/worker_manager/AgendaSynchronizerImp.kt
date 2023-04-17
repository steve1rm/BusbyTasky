package me.androidbox.data.worker_manager

import androidx.work.*
import me.androidbox.domain.work_manager.AgendaSynchronizer
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AgendaSynchronizerImp @Inject constructor(
    private val workManager: WorkManager,
) : AgendaSynchronizer {

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
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            syncWorkerRequest,
        )

        return syncWorkerRequest.id
    }
}