package me.androidbox.data.remote.model.response

import me.androidbox.data.remote.model.AttendeeDto
import me.androidbox.data.remote.model.PhotoDto

data class EventResponseDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val listOfAttendee: List<AttendeeDto>,
    val listOfPhoto: List<PhotoDto>
)
