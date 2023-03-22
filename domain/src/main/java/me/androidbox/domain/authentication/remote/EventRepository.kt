package me.androidbox.domain.authentication.remote

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event

interface EventRepository {
    fun getEventsFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<ResponseState<List<Event>>>

    suspend fun insertEvent(eventEntity: Event)

    suspend fun deleteEventById(id: String)
}