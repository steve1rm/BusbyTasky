package me.androidbox.data.worker_manager

import androidx.work.WorkManager
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.work_manager.UploadEventWorker
import javax.inject.Inject

class UploadEventWorkerImp @Inject constructor(
    private val workManager: WorkManager
) : UploadEventWorker {

    override suspend fun uploadEvent(event: Event, isEditModel: Boolean) {
        TODO("Not yet implemented")
    }
}