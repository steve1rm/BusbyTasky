package me.androidbox.domain.alarm_manager

import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.Task

data class AlarmItem(
    val agendaId: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val agendaType: AgendaType
)

fun Event.toAlarmItem(): AlarmItem {
    return AlarmItem(
        agendaId = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        agendaType = AgendaType.EVENT
    )
}

fun Task.toAlarmItem(): AlarmItem {
    return AlarmItem(
        agendaId = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        agendaType = AgendaType.TASK
    )
}

enum class AgendaType {
    EVENT,
    TASK,
    REMINDER
}