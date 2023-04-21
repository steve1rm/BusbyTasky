package me.androidbox.domain.work_manager

import me.androidbox.domain.agenda.model.Event


interface UploadEvent {
    fun upload(event: Event, isEditMode: Boolean)
}