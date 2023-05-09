package me.androidbox.presentation.task.screen

import me.androidbox.component.agenda.AlarmReminderItem
import java.time.ZonedDateTime

sealed interface TaskDetailScreenEvent {
    data class OnSaveTitleOrDescription(val title: String, val description: String) : TaskDetailScreenEvent
    data class OnFromDateChanged(val from: ZonedDateTime) : TaskDetailScreenEvent
    data class OnAlarmReminderChanged(val reminderItem: AlarmReminderItem) : TaskDetailScreenEvent
    data class OnShowDeleteEventAlertDialog(val shouldShowDeleteAlertDialog: Boolean): TaskDetailScreenEvent
    data class OnDeleteTask(val taskId: String) : TaskDetailScreenEvent
    data class OnSaveTaskDetails(val taskId: String): TaskDetailScreenEvent
    data class OnShowAlarmReminderDropdown(val shouldOpen: Boolean) : TaskDetailScreenEvent
    data class OnEditModeChangeStatus(val isEditMode: Boolean = true) : TaskDetailScreenEvent
    data class OnShowSnackBar(val showSnackBar: Boolean, val snackbarDisplayMessage: String = "", val snackbarActionMessage: String = ""): TaskDetailScreenEvent
}