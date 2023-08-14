package me.androidbox.data.local.agenda

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.TaskSyncEntity
import me.androidbox.data.mapper.toTask
import me.androidbox.data.mapper.toTaskDto
import me.androidbox.data.mapper.toTaskEntity
import me.androidbox.data.remote.network.task.TaskService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.domain.alarm_manager.toAlarmItem
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.task.repository.TaskRepository

class TaskRepositoryImp @Inject constructor(
    private val taskDao: TaskDao,
    private val taskService: TaskService,
    private val alarmScheduler: AlarmScheduler
) : TaskRepository {
    override suspend fun insertTask(task: Task): ResponseState<Unit> {
        taskDao.insertTask(task.toTaskEntity())
        val alarmItem = task.toAlarmItem()
        alarmScheduler.scheduleAlarmReminder(alarmItem)

        val result = checkResult {
            /** If we fail here insert the CREATE into the sync table */
            taskService.createTask(task.toTaskDto())
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                insertSyncTask(task.id, SyncAgendaType.CREATE)
           //     ResponseState.Failure(throwable)
            }
        )
    }

    override suspend fun updateTask(task: Task): ResponseState<Unit> {
        taskDao.insertTask(task.toTaskEntity())
        val alarmItem = task.toAlarmItem()
        alarmScheduler.scheduleAlarmReminder(alarmItem)

        val result = checkResult {
            /** If we fail here insert the UPDATE into the sync table */
            taskService.updateTask(task.toTaskDto())
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                insertSyncTask(task.id, SyncAgendaType.UPDATE)
           //     ResponseState.Failure(throwable)
            }
        )
    }

    override suspend fun deleteTaskById(taskId: String): ResponseState<Unit> {
        taskDao.deleteTaskById(taskId)
        return ResponseState.Success(Unit)

/*
        val result = checkResult {
            taskService.deleteTask(taskId)
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                insertSyncTask(taskId, SyncAgendaType.DELETE)
                ResponseState.Failure(throwable)
            }
        )
*/
    }

    override fun getTaskById(taskId: String): Flow<ResponseState<Task>> {
        return flow<ResponseState<Task>> {
            val result = checkResult {
                emit(ResponseState.Loading)
                taskDao.getTaskById(taskId)
            }

            result.fold(
                onSuccess = { taskEntity ->
                    emit(ResponseState.Success(taskEntity.toTask()))
                },
                onFailure = { throwable ->
                   emit(ResponseState.Failure(throwable.message.orEmpty()))
                }
            )
        }
    }

    override suspend fun uploadTask(task: Task): ResponseState<Unit> {
        val result = checkResult {
            taskService.createTask(task.toTaskDto())
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable.message.orEmpty())
            }
        )
    }

    override suspend fun insertSyncTask(taskId: String, syncAgendaType: SyncAgendaType): ResponseState<Unit> {
        val result = checkResult {
            taskDao.insertSyncEvent(TaskSyncEntity(taskId, syncAgendaType))
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable.message.orEmpty())
            }
        )
    }
}