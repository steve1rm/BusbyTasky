package me.androidbox.domain.agenda.model

data class SyncAgenda(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)
