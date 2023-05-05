package me.androidbox.domain.work_manager

import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.constant.UpdateModeType


interface UploadEvent {
    fun upload(event: Event, updateModeType: UpdateModeType)
}