package me.androidbox.presentation.task.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.presentation.task.screen.TaskDetailScreenEvent
import me.androidbox.presentation.task.screen.TaskDetailScreenState
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
) : ViewModel() {
    private val _taskDetailScreenState: MutableStateFlow<TaskDetailScreenState> = MutableStateFlow(
        TaskDetailScreenState()
    )
    val taskDetailScreenState = _taskDetailScreenState.asStateFlow()

    fun onTaskDetailScreenEvent(taskDetailScreenEvent: TaskDetailScreenEvent) {
        when(taskDetailScreenEvent) {
            is TaskDetailScreenEvent.OnAlarmReminderChanged -> TODO()
            is TaskDetailScreenEvent.OnDeleteTask -> TODO()
            is TaskDetailScreenEvent.OnFromDate -> TODO()
            is TaskDetailScreenEvent.OnSaveTitleOrDescription -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        taskTitle = taskDetailScreenEvent.title,
                        taskDescription = taskDetailScreenState.taskDescription
                    )
                }
            }
            is TaskDetailScreenEvent.OnShowDeleteEventAlertDialog -> TODO()
            TaskDetailScreenEvent.OnSaveTaskDetails -> TODO()
            is TaskDetailScreenEvent.OnShowAlarmReminderDropdown -> TODO()
        }
    }
}