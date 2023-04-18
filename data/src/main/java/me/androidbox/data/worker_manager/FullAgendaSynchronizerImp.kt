package me.androidbox.data.worker_manager

import androidx.work.WorkManager
import me.androidbox.domain.work_manager.FullAgendaSynchronizer
import javax.inject.Inject

class FullAgendaSynchronizerImp @Inject constructor(
    private val workManager: WorkManager) : FullAgendaSynchronizer {

    override suspend fun sync() {

    }

    override suspend fun cancel() {
        workManager.cancelAllWork()
    }
}