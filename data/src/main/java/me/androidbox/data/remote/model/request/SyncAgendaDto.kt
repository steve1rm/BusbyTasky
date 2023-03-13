package me.androidbox.data.remote.model.request

/**
 * /syncAgenda
 * POST
 * Sync's locally deleted agenda items with the API
 *
 * */
data class SyncAgendaDto(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)
