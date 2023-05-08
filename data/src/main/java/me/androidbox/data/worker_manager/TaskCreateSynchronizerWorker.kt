package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
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
    private val taskDao: TaskDao,
    private val reminderService: reminderService,
    private val reminderDao: ReminderDao,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        if(runAttemptCount > RETRY_COUNT) {
            Result.failure()
        }
        else {
            Result.retry()
        }

        /** Get all the created tasks */
        val createdTasks = taskDao.getAllTasksBySyncType(SyncAgendaType.CREATE)

        if (createdTasks.isNotEmpty()) {
            val responseState = checkResult{

                supervisorScope {
                    launch {
                        createdTasks.map { taskEntity ->
                            /* Create the task on remote */
                            taskService.createTask(taskEntity.toTaskDto())
                            /* If success will run this delete - else if loop next next one */
                            taskDao.deleteSyncTaskById(taskEntity.id)
                        }
                    }
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

        return Result.success()
    }
}