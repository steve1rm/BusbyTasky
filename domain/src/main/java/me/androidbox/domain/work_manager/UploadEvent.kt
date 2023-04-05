package me.androidbox.domain.work_manager

import me.androidbox.domain.authentication.model.Event

interface UploadEvent {
    suspend fun upload(event: Event, isEditMode: Boolean)
}