package me.androidbox.domain.work_manager

import me.androidbox.domain.authentication.model.Event

interface UploadEventWorker {
    suspend fun uploadEvent(event: Event, isEditModel: Boolean)
}