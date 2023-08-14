package me.androidbox.presentation.task.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.domain.DateTimeFormatterProvider.toZoneDateTime
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.constant.UpdateModeType
import me.androidbox.domain.task.repository.TaskRepository
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.presentation.alarm_manager.AlarmReminderProvider
import me.androidbox.presentation.navigation.Screen.Companion.ID
import me.androidbox.presentation.navigation.Screen.Companion.MENU_ACTION_TYPE
import me.androidbox.presentation.task.screen.TaskDetailScreenEvent
import me.androidbox.presentation.task.screen.TaskDetailScreenState

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _taskDetailScreenState: MutableStateFlow<TaskDetailScreenState> = MutableStateFlow(
        TaskDetailScreenState()
    )
    val taskDetailScreenState = _taskDetailScreenState.asStateFlow()

    init {
        val menuActionType = savedStateHandle.get<String>(MENU_ACTION_TYPE)
        val taskId = savedStateHandle.get<String>(ID) ?: ""

        menuActionType?.let { actionType ->
            /** Actions for opening and editing */
            when (actionType) {
                AgendaMenuActionType.OPEN.name -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isEditMode = false,
                            updateModeType = UpdateModeType.UPDATE,
                            taskId = taskId
                        )
                    }
                    fetchTaskById(taskId)
                }
                AgendaMenuActionType.EDIT.name -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isEditMode = true,
                            updateModeType = UpdateModeType.UPDATE,
                            taskId = taskId
                        )
                    }
                    fetchTaskById(taskId)
                }
                else -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isEditMode = true,
                            updateModeType = UpdateModeType.CREATE,
                            taskId = UUID.randomUUID().toString()
                        )
                    }
                }
            }
        }
    }

    fun onTaskDetailScreenEvent(taskDetailScreenEvent: TaskDetailScreenEvent) {
        when (taskDetailScreenEvent) {
            is TaskDetailScreenEvent.OnAlarmReminderChanged -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        alarmReminderItem = taskDetailScreenEvent.reminderItem
                    )
                }
            }
            is TaskDetailScreenEvent.OnDeleteTask -> {
                deleteTask(taskDetailScreenEvent.taskId)
            }
            is TaskDetailScreenEvent.OnFromDateChanged -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        from = taskDetailScreenEvent.from
                    )
                }
            }
            is TaskDetailScreenEvent.OnSaveTitleOrDescription -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        taskTitle = taskDetailScreenEvent.title,
                        taskDescription = taskDetailScreenState.taskDescription
                    )
                }
            }
            is TaskDetailScreenEvent.OnShowDeleteEventAlertDialog -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        shouldShowDeleteAlertDialog = taskDetailScreenEvent.shouldShowDeleteAlertDialog
                    )
                }
            }
            is TaskDetailScreenEvent.OnSaveTaskDetails -> {
                insertTaskDetails(taskDetailScreenEvent.taskId)
            }
            is TaskDetailScreenEvent.OnShowAlarmReminderDropdown -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        shouldOpenDropdown = taskDetailScreenEvent.shouldOpen
                    )
                }
            }

            is TaskDetailScreenEvent.OnEditModeChangeStatus -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        isEditMode = taskDetailScreenEvent.isEditMode
                    )
                }
            }

            is TaskDetailScreenEvent.OnShowSnackBar -> {
                _taskDetailScreenState.update { taskDetailScreenState ->
                    taskDetailScreenState.copy(
                        showSnackBar = taskDetailScreenEvent.showSnackBar,
                        snackbarDisplayMessage = taskDetailScreenEvent.snackbarDisplayMessage,
                        snackbarActionMessage = taskDetailScreenEvent.snackbarActionMessage
                    )
                }
            }
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
            val responseState = when (taskDetailScreenState.value.updateModeType) {
                UpdateModeType.UPDATE -> {
                    taskRepository.updateTask(task)
                }
                UpdateModeType.CREATE -> {
                    taskRepository.insertTask(task)
                }
            }
            when (responseState) {
                ResponseState.Loading -> {
                    println("Show Loading spinner...")
                }
                is ResponseState.Success -> {
                    _taskDetailScreenState.update { taskDetailScreenState ->
                        taskDetailScreenState.copy(
                            isSaved = true
                        )
                    }
                }
                is ResponseState.Failure -> {
                    onTaskDetailScreenEvent(TaskDetailScreenEvent.OnShowSnackBar(
                        showSnackBar = true,
                        snackbarDisplayMessage = responseState.error?: "Oops, something went wrong",
                        snackbarActionMessage = "Try Again"
                    ))
                }
            }
        }
    }

    private fun fetchTaskById(taskId: String) {
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).collectLatest { responseState ->
                when (responseState) {
                    ResponseState.Loading -> Unit
                    is ResponseState.Success -> {
                        val task = responseState.data
                        _taskDetailScreenState.update { taskDetailScreenState ->
                            taskDetailScreenState.copy(
                                taskId = task.id,
                                taskTitle = task.title,
                                taskDescription = task.description,
                                from = task.startDateTime.toZoneDateTime(),
                                remindAt = task.remindAt,
                                isDone = task.isDone
                            )
                        }
                    }
                    is ResponseState.Failure -> {
                        Log.d("TASK_DETAIL", "${responseState.error}")
                    }
                }
            }
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.deleteTaskById(taskId)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }
}