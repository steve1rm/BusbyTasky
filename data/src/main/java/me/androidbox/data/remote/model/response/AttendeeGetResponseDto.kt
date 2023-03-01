package me.androidbox.data.remote.model.response

import me.androidbox.data.remote.model.AttendeeDto

data class AttendeeGetResponseDto(
    val isExistingUser: Boolean,
    val attendee: AttendeeDto
)
