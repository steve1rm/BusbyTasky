package me.androidbox.data.remote.model.response

/**
 * /Agenda
 * GET
 * Returns the agenda for a given day
 *
 * Query parameters
 * timezone i.e. Europe/Berlin
 * time i.e. System.currentTimeMillis()
 *
 * Response: AgendaDto
 * */
data class AgendaDto(
    val events: List<EventDto>,
    val tasks: List<TaskDto>,
    val reminders: List<ReminderDto>
)
