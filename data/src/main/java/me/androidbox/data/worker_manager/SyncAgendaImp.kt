package me.androidbox.data.worker_manager

import androidx.work.WorkManager
import me.androidbox.domain.work_manager.SyncAgenda
import javax.inject.Inject

class SyncAgendaImp @Inject constructor(
    private val workManager: WorkManager
) : SyncAgenda {

    override suspend fun sync(syncAgendaItems: SyncAgenda) {

    }
}