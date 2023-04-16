package me.androidbox.domain.authentication.remote

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.agenda.model.Agenda
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.constant.SyncAgendaType

interface AgendaLocalRepository {
    fun fetchAgenda(startTimeStamp: Long, endTimeStamp: Long): Flow<ResponseState<Agenda>>
    suspend fun deleteEventSyncType(syncAgendaType: SyncAgendaType)
}