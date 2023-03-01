package me.androidbox.data.remote.model.response

data class AttendeeGetResponseDto(
    val isExistingUser: Boolean,
    val attendee: AttendeeDto
)
