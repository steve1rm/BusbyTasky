package me.androidbox.data.remote.model

data class SyncAgendaDto(
    val deletedEventId: List<String>,
    val deletedTaskId: List<String>,
    val deletedReminderId: List<String>
)
