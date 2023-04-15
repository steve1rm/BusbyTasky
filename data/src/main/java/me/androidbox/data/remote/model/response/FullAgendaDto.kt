package me.androidbox.data.remote.model.response

/**
 * /fullAgenda
 * GET
 * Returns all events, tasks and & reminders. This is intended to sync
 * the device's local cache. Don't use this to show agenda items for a specific day. For that
 * call `/agenda` instead
 *
 * */
data class FullAgendaDto(
    val events: List<EventDto>,
    val tasks: List<TaskDto>,
    val reminders: List<ReminderDto>
)
