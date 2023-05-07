package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.mapper.toTaskDto
import me.androidbox.data.remote.network.task.TaskService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT
import me.androidbox.domain.constant.SyncAgendaType

@HiltWorker
class TaskCreateSynchronizerWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val taskService: TaskService,
    private val taskDao: TaskDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val createdTasks: Deferred<List<TaskEntity>>

        /** Get all the created tasks */
        supervisorScope {
            createdTasks = async {
                taskDao.getAllTasksBySyncType(SyncAgendaType.CREATE).map {
                    it.task
                }
            }
        }

        val result = if (createdTasks.await().isNotEmpty()) {
            if(runAttemptCount > RETRY_COUNT) {
                Result.failure()
            }
            else {
                Result.retry()
            }

            val responseState = checkResult{
                val createdTasksEntity = createdTasks.await()

                supervisorScope {
                    createdTasksEntity.map { taskEntity ->
                        launch {
                            taskService.createTask(taskEntity.toTaskDto())
                        }
                    }.forEach { it.join() }
                }
            }

            responseState.fold(
                onSuccess = {
                    taskDao.deleteSyncTasksBySyncType(SyncAgendaType.CREATE)
                    Result.success()
                },
                onFailure = {
                    Result.retry()
                }
            )
        }
        else {
            /** There was nothing to sync as the sync table was empty */
            Result.success()
        }

        return result
    }
}