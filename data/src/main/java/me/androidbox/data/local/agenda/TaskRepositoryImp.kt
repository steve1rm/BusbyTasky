package me.androidbox.data.local.agenda

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.mapper.toTask
import me.androidbox.data.mapper.toTaskEntity
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.network.task.TaskService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.task.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(
    private val taskDao: TaskDao,
    private val taskService: TaskService
) : TaskRepository {
    override suspend fun insertTask(task: Task): ResponseState<Unit> {
        val result = checkResult {
            taskDao.insertTask(task.toTaskEntity())
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)
            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )
    }

    override suspend fun deleteTaskById(taskId: String): ResponseState<Unit> {
        val result = checkResult {
            taskDao.deleteTaskById(taskId)
        }

        return result.fold(
            onSuccess = {
                ResponseState.Success(Unit)

            },
            onFailure = { throwable ->
                ResponseState.Failure(throwable)
            }
        )
    }

    override fun getTaskById(taskId: String): Flow<ResponseState<Task>> {
        return flow<ResponseState<Task>> {
            val result = checkResult {
                taskDao.getTaskById(taskId)
            }

            result.fold(
                onSuccess = { taskEntity ->
                    emit(ResponseState.Success(taskEntity.toTask()))
                },
                onFailure = { throwable ->
                    emit(ResponseState.Failure(throwable))
                }
            )
        }
    }

    override suspend fun insertSyncTask(taskId: String, syncAgendaType: SyncAgendaType): ResponseState<Unit> {
        TODO()
    }
}