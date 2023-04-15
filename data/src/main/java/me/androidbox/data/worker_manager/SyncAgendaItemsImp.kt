package me.androidbox.data.worker_manager

import androidx.work.WorkManager
import me.androidbox.domain.work_manager.SyncAgendaItems
import javax.inject.Inject

class SyncAgendaItemsImp @Inject constructor(
    private val workManager: WorkManager
) : SyncAgendaItems {

    override suspend fun sync(syncAgendaItems: SyncAgendaItems) {

    }
}