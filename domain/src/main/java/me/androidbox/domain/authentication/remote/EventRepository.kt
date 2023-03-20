package me.androidbox.domain.authentication.remote

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event

interface EventRepository {
    suspend fun getEventsFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): ResponseState<List<Event>>

    suspend fun insertEvent(event: Event): ResponseState<Unit>

    suspend fun deleteEventById(id: String): ResponseState<Unit>
}