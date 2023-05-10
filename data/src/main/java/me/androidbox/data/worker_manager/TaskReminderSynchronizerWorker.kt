package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.ReminderEntity
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.mapper.toReminderDto
import me.androidbox.data.mapper.toTaskDto
import me.androidbox.data.remote.network.reminder.ReminderService
import me.androidbox.data.remote.network.task.TaskService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT
import me.androidbox.domain.constant.SyncAgendaType

@HiltWorker
/** TODO Rename to something more readable as this is not just related to tasks */
class TaskReminderSynchronizerWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val taskService: TaskService,
    private val taskDao: TaskDao,
    private val reminderService: ReminderService,
    private val reminderDao: ReminderDao,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        if(runAttemptCount > RETRY_COUNT) {
            Result.failure()
        }
        else {
            Result.retry()
        }

        val createdTasks: Deferred<List<TaskEntity>>
        val updatedTasks: Deferred<List<TaskEntity>>
        val createdReminders: Deferred<List<ReminderEntity>>
        val updatedReminders: Deferred<List<ReminderEntity>>

        /** Get all the created, updated tasks and reminders
         *  TODO As these will run sequentially - is it better to use supervisor scope and run them in parallel? */
        supervisorScope {
            createdTasks = async {
                val taskIds = taskDao.getAllTasksBySyncType(SyncAgendaType.CREATE)

                taskIds.map { id ->
                    taskDao.getTaskById(id)
                }
            }

            updatedTasks = async {
                val taskIds = taskDao.getAllTasksBySyncType(SyncAgendaType.UPDATE)

                taskIds.map { id ->
                    taskDao.getTaskById(id)
                }
            }

            createdReminders = async {
                val reminderIds = reminderDao.getAllRemindersBySyncType(SyncAgendaType.CREATE)

                reminderIds.map { id ->
                    reminderDao.getReminderById(id)
                }
            }

            updatedReminders = async {
                val reminderIds = reminderDao.getAllRemindersBySyncType(SyncAgendaType.UPDATE)

                reminderIds.map { id ->
                    reminderDao.getReminderById(id)
                }
            }
        }

        /** TODO I am using supervisor scope to launch so if one fails the other will still run
         *  However, will the worker return failure if a single one fails even though some failed?
         *  My checkResult will run everything in this block */
        val responseState = checkResult {
            supervisorScope {
                val syncedJobs = listOf(launch {
                    createdTasks.await().forEach { taskEntity ->
                        /* Create the task on remote */
                        val result = checkResult {
                            taskService.createTask(taskEntity.toTaskDto())
                        }
                        if(result.isSuccess) {
                            /* If success will run this delete - else if loop next next one */
                            taskDao.deleteSyncTaskById(taskEntity.id)
                        }
                    }
                },

                launch {
                    updatedTasks.await().forEach { taskEntity ->
                        val result = checkResult {
                            taskService.updateTask(taskEntity.toTaskDto())
                        }
                        if(result.isSuccess) {
                            taskDao.deleteSyncTaskById(taskEntity.id)
                        }
                    }
                },

                launch {
                    createdReminders.await().forEach { reminderEntity ->
                        val result = checkResult {
                            reminderService.createReminder(reminderEntity.toReminderDto())
                        }
                        if(result.isSuccess) {
                            reminderDao.deleteSyncReminderById(reminderEntity.id)
                        }
                    }
                },

                launch {
                    updatedReminders.await().forEach { reminderEntity ->
                        val result = checkResult {
                            reminderService.updateReminder(reminderEntity.toReminderDto())
                        }
                        if(result.isSuccess) {
                            reminderDao.deleteReminderById(reminderEntity.id)
                        }
                    }
                })

                /** TODO Waiting for all jobs to complete */
                syncedJobs.joinAll()
            }
        }

        return responseState.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )
    }
}
