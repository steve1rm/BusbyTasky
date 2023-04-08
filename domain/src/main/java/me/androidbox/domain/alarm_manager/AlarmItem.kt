package me.androidbox.domain.alarm_manager

import me.androidbox.domain.agenda.model.Event

data class AlarmItem(
    val agendaId: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val agendaType: AgendaType
)

fun Event.toAlarmItem(agendaType: AgendaType): AlarmItem {
    return AlarmItem(
        agendaId = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        agendaType = agendaType
    )
}

enum class AgendaType {
    EVENT,
    TASK,
    REMINDER
}