package me.androidbox.domain.agenda.model

import me.androidbox.domain.alarm_manager.AgendaType

data class Event(
    override val id: String,
    override val title: String,
    override val description: String,
    override val startDateTime: Long,
    override val remindAt: Long,
    override val agendaType: AgendaType = AgendaType.EVENT,
    val endDateTime: Long,
    val eventCreatorId: String,
    val isUserEventCreator: Boolean,
    val host: String,
    val attendees: List<Attendee>,
    val photos: List<String>,
) : AgendaItem(
    id = id,
    title = title,
    description = description,
    startDateTime = startDateTime,
    remindAt = remindAt,
    agendaType = agendaType
)
