package me.androidbox.presentation.task.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.presentation.task.screen.TaskDetailScreenEvent
import me.androidbox.presentation.task.screen.TaskDetailScreenState
import javax.inject.Inject

class TaskDetailViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
) {
    private val _taskDetailScreenState: MutableStateFlow<TaskDetailScreenState> = MutableStateFlow(
        TaskDetailScreenState()
    )
    val taskDetailScreenState = _taskDetailScreenState.asStateFlow()

    fun onTaskDetailScreenEvent(taskDetailScreenEvent: TaskDetailScreenEvent) {
        when(taskDetailScreenEvent) {
            is TaskDetailScreenEvent.OnAlarmReminderChanged -> TODO()
            is TaskDetailScreenEvent.OnDeleteTask -> TODO()
            is TaskDetailScreenEvent.OnFromDate -> TODO()
            is TaskDetailScreenEvent.OnSaveTitleOrDescription -> TODO()
            is TaskDetailScreenEvent.OnShowDeleteEventAlertDialog -> TODO()
        }
    }
}