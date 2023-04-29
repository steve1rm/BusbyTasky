package me.androidbox.domain.authentication.remote

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.constant.SyncAgendaType

interface EventRepository {
    fun getEventsFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<ResponseState<List<Event>>>

    suspend fun insertEvent(event: Event): ResponseState<Unit>

    suspend fun deleteEventById(id: String): ResponseState<Unit>

    fun getEventById(id: String): Flow<ResponseState<Event>>

    suspend fun insertSyncEvent(eventId: String, syncAgendaType: SyncAgendaType): ResponseState<Unit>
}