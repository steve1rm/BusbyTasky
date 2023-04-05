package me.androidbox.domain.alarm_manager

import me.androidbox.domain.authentication.model.Event

data class AlarmItem(
    val agendaId: String,
    val title: String,
    val description: String,
    val remindAtInMilliSecs: Long,
    val agendaType: AgendaType
)

fun Event.toAlarmItem(agendaType: AgendaType): AlarmItem {
    return AlarmItem(
        agendaId = this.id,
        title = this.title,
        description = this.description,
        remindAtInMilliSecs = this.remindAt * 1000,
        agendaType = agendaType
    )
}

enum class AgendaType {
    EVENT,
    TASK,
    REMINDER
}