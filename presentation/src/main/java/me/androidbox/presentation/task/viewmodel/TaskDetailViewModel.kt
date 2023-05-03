package me.androidbox.presentation.task.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.data.local.agenda.TaskRepositoryImp
import me.androidbox.data.mapper.toTaskEntity
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.domain.alarm_manager.toAlarmItem
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.presentation.alarm_manager.AlarmReminderProvider
import me.androidbox.presentation.navigation.Screen.TaskDetailScreen.MENU_ACTION_TYPE
import me.androidbox.presentation.navigation.Screen.TaskDetailScreen.TASK_ID
import me.androidbox.presentation.task.screen.TaskDetailScreenEvent
import me.androidbox.presentation.task.screen.TaskDetailScreenState
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val taskRepositoryImp: TaskRepositoryImp,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _taskDetailScreenState: MutableStateFlow<TaskDetailScreenState> = MutableStateFlow(
        TaskDetailScreenState()
    )
    val taskDetailScreenState = _taskDetailScreenState.asStateFlow()

    init {
        val menuActionType = savedStateHandle.get<String>(MENU_ACTION_TYPE)
        val taskId = savedStateHandle.get<String>(TASK_ID)

        if(menuActionType != null && taskId != null) {
            /** Actions for opening and editing */
            when(menuActionType) {
                AgendaMenuActionType.OPEN.name -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isEditMode = false,
                            taskId = taskDetailScreenState.taskId
                        )
                    }
                }
                AgendaMenuActionType.EDIT.name -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isEditMode = true,
                            taskId = taskDetailScreenState.taskId
                        )
                    }
                }
            }
        }
        else {
            /** Creating new tasks */
            _taskDetailScreenState.update { taskDetailScreenState ->
                taskDetailScreenState.copy(
                    isEditMode = false,
                    taskId = UUID.randomUUID().toString()
                )
            }
        }
    }

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
            is TaskDetailScreenEvent.OnSaveTaskDetails -> {
                insertTaskDetails(taskDetailScreenEvent.taskId)
            }
            is TaskDetailScreenEvent.OnShowAlarmReminderDropdown -> TODO()
        }
    }

    private fun insertTaskDetails(taskId: String) {
        val startDateTime = AlarmReminderProvider.getCombinedFromDate(taskDetailScreenState.value.from)
        val remindAt = AlarmReminderProvider.getRemindAt(taskDetailScreenState.value.alarmReminderItem, startDateTime)

        val task = Task(
            id = taskId,
            title = taskDetailScreenState.value.taskTitle,
            description = taskDetailScreenState.value.taskDescription,
            startDateTime = taskDetailScreenState.value.from.toEpochSecond(),
            remindAt = remindAt.toEpochSecond(),
            isDone = taskDetailScreenState.value.isDone)

        viewModelScope.launch {
            taskRepositoryImp.insertTask(task)

            when(val responseState = taskRepositoryImp.insertTask(task)) {
                ResponseState.Loading -> TODO()
                is ResponseState.Success -> {
                    val alarmItem = task.toAlarmItem()
                    alarmScheduler.scheduleAlarmReminder(alarmItem)

                }
                is ResponseState.Failure -> {
                    Log.e("TASK_INSERT", "${responseState.error.message}")
                    /**   */
                }
            }

        }
    }
}