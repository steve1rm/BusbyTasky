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
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.ReminderEntity
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.mapper.toTaskDto
import me.androidbox.data.remote.network.reminder.ReminderService
import me.androidbox.data.remote.network.task.TaskService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.constant.SyncAgendaType

@HiltWorker
class TaskCreateSynchronizerWorker @AssistedInject constructor(
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

        val responseState = checkResult {
            supervisorScope {
                val createTaskJob = launch {
                    createdTasks.await().map { taskEntity ->
                        /* Create the task on remote */
                        taskService.createTask(taskEntity.toTaskDto())
                        /* If success will run this delete - else if loop next next one */
                        taskDao.deleteSyncTaskById(taskEntity.id)
                    }
                }

                val updateTaskJob = launch {
                    updatedTasks.await().forEach { taskEntity ->
                        taskService.updateTask(taskEntity.toTaskDto())
                        taskDao.deleteSyncTaskById(taskEntity.id)
                    }
                }

                val createReminderJob = launch {
                    createdReminders.await().forEach { reminderEntity ->
                        reminderService.createReminder(reminderEntity)
                        reminderDao.deleteSyncReminderById(reminderEntity.id)
                    }
                }

                val updateReminderJob = launch {
                    updatedReminders.await().forEach { reminderEntity ->
                        reminderService.updateReminder(reminderEntity)
                        reminderDao.deleteReminderById(reminderEntity.id)
                    }
                }

                createTaskJob.join()
                updateTaskJob.join()
                createReminderJob.join()
                updateReminderJob.join()
            }
        }

        return  responseState.fold(
            onSuccess = {
                taskDao.deleteSyncTasksBySyncType(SyncAgendaType.CREATE)
                taskDao.deleteSyncTasksBySyncType(SyncAgendaType.UPDATE)
                reminderDao.deleteSyncRemindersBySyncType(SyncAgendaType.CREATE)
                reminderDao.deleteSyncRemindersBySyncType(SyncAgendaType.UPDATE)
                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )
    }
}
