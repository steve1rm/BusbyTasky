package me.androidbox.data.worker_manager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import me.androidbox.domain.work_manager.TaskCreateSynchronizer
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TaskCreateSynchronizerImp @Inject constructor(
    private val workManager: WorkManager
) : TaskCreateSynchronizer {

    companion object {
        const val SYNC_INTERVAL = 30L
        const val TASK_CREATE_SYNC = "task_create_sync"
    }

    override fun sync(): UUID {
        val taskCreateSynWorkerRequest = PeriodicWorkRequestBuilder<TaskCreateSynchronizerWorker>(
            repeatInterval = SYNC_INTERVAL,
            repeatIntervalTimeUnit = TimeUnit.MINUTES)
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            ).build()

        workManager.enqueueUniquePeriodicWork(
            TASK_CREATE_SYNC,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            taskCreateSynWorkerRequest)

        return taskCreateSynWorkerRequest.id
    }

    override fun cancel() {
        workManager.cancelUniqueWork(TASK_CREATE_SYNC)
    }
}