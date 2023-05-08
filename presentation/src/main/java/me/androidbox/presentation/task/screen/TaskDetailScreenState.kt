package me.androidbox.presentation.task.screen

import me.androidbox.component.agenda.AlarmReminderItem
import me.androidbox.domain.constant.UpdateModeType
import java.time.ZoneId
import java.time.ZonedDateTime

data class TaskDetailScreenState(
    val taskTitle: String = "New Event",
    val taskDescription: String = "Description",
    val from: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val alarmReminderItem: AlarmReminderItem = AlarmReminderItem.TEN_MINUTES,
    val isEditMode: Boolean = false,
    val shouldOpenDropdown: Boolean = false,
    val taskId: String = "",
    val isDone: Boolean = false,
    val remindAt: Long = 0L,
    val shouldShowDeleteAlertDialog: Boolean = false,
    val isSaved: Boolean = false,
    val updateModeType: UpdateModeType = UpdateModeType.CREATE
)
