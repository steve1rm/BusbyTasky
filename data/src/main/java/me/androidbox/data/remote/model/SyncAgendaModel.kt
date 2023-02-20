package me.androidbox.data.remote.model

data class SyncAgendaModel(
    val deletedEventId: List<String>,
    val deletedTaskId: List<String>,
    val deletedReminderId: List<String>
)
