package me.androidbox.domain.alarm_manager

import me.androidbox.domain.authentication.model.Event

data class AlarmItem(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val agendaType: AgendaType
)

fun Event.toAlarmItem(agendaType: AgendaType): AlarmItem {
    return AlarmItem(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt * 1000,
        agendaType = agendaType
    )
}

enum class AgendaType {
    EVENT,
    TASK,
    REMINDER
}