package me.androidbox.domain.agenda.model

import me.androidbox.domain.alarm_manager.AgendaType

sealed class AgendaItem(
    open val id: String,
    open val title: String,
    open val description: String,
    open val startDateTime: Long,
    open val remindAt: Long,
    open val agendaType: AgendaType
)
