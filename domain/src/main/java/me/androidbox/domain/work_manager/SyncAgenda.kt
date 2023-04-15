package me.androidbox.domain.work_manager

interface SyncAgenda {
    suspend fun sync(syncAgendaItems: SyncAgenda)
}