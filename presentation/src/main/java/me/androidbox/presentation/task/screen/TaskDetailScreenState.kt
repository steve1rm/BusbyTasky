package me.androidbox.presentation.task.screen

import me.androidbox.component.agenda.AlarmReminderItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class TaskDetailScreenState(
    val taskTitle: String = "",
    val taskDescription: String = "",
    val from: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val alarmReminderItem: AlarmReminderItem = AlarmReminderItem.TEN_MINUTES
)
