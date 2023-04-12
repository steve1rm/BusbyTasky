package me.androidbox.domain.authentication.remote

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.agenda.model.Agenda
import me.androidbox.domain.authentication.ResponseState

interface AgendaRepository {
    fun fetchAgenda(startTimeStamp: Long, endTimeStamp: Long): Flow<ResponseState<Agenda>>
}