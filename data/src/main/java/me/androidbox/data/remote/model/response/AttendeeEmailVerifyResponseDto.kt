package me.androidbox.data.remote.model.response

data class AttendeeEmailVerifyResponseDto(
    val doesUserExist: Boolean,
    val attendee: AttendeeDto
)
