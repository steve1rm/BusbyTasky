package me.androidbox.data.worker_manager

import androidx.work.WorkManager
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.work_manager.UploadEvent
import javax.inject.Inject

class UploadEventImp @Inject constructor(
    private val workManager: WorkManager
) : UploadEvent {

    override suspend fun upload(event: Event, isEditMode: Boolean) {
        println("event: ${event.id}")
    }
}