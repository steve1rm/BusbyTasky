package me.androidbox.domain.agenda.model

import me.androidbox.domain.alarm_manager.AgendaType

data class Reminder(
    override val id: String,
    override val title: String,
    override val description: String,
    override val startDateTime: Long,
    override val remindAt: Long,
    override val agendaType: AgendaType = AgendaType.REMINDER
) : AgendaItem(
    id = id,
    title = title,
    description = description,
    startDateTime = startDateTime,
    remindAt = remindAt,
    agendaType = agendaType
)
