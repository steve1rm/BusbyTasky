package me.androidbox.data.worker_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.androidbox.data.remote.model.request.SyncAgendaDto
import me.androidbox.data.remote.network.agenda.AgendaService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.data.worker_manager.SyncAgendaItemsImp.Companion.DELETED_EVENT_IDS
import me.androidbox.data.worker_manager.SyncAgendaItemsImp.Companion.DELETED_REMINDER_IDS
import me.androidbox.data.worker_manager.SyncAgendaItemsImp.Companion.DELETED_TASK_IDS
import me.androidbox.data.worker_manager.constant.Constant.RETRY_COUNT

@HiltWorker
class SyncAgendaItemsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val agendaService: AgendaService
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val deletedEventIds = inputData.getStringArray(DELETED_EVENT_IDS) ?: emptyArray()
        val deletedTaskIds = inputData.getStringArray(DELETED_TASK_IDS) ?: emptyArray()
        val deletedReminderIds = inputData.getStringArray(DELETED_REMINDER_IDS) ?: emptyArray()


        val responseResult = checkResult {
            val syncAgendaDto = SyncAgendaDto(
                deletedEventIds = deletedEventIds.toList(),
                deletedTaskIds = deletedTaskIds.toList(),
                deletedReminderIds = deletedReminderIds.toList())

            agendaService.syncAgenda(syncAgendaDto)
        }

        if(runAttemptCount > RETRY_COUNT) {
            Result.failure()
        }

        val result = responseResult.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {
                Result.retry()
            }
        )

        return result
    }
}