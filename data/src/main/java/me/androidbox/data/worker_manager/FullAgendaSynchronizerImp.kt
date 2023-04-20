package me.androidbox.data.worker_manager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import me.androidbox.domain.work_manager.FullAgendaSynchronizer
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FullAgendaSynchronizerImp @Inject constructor(
    private val workManager: WorkManager) : FullAgendaSynchronizer {

    companion object {
        const val SYNC_INTERVAL = 30L
        const val FULL_AGENDA_SYNC = "full_agenda_sync"
    }

    override suspend fun sync(): UUID {
        val fullSynWorkerRequest = PeriodicWorkRequestBuilder<SyncFullAgendaItemsWorker>(
            repeatInterval = SYNC_INTERVAL,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            ).build()

        workManager.enqueueUniquePeriodicWork(
            FULL_AGENDA_SYNC,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            fullSynWorkerRequest)

        return fullSynWorkerRequest.id
    }

    override suspend fun cancel() {
        workManager.cancelAllWork()
    }
}