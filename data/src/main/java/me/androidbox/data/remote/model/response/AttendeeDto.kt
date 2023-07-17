package me.androidbox.data.remote.model.response

data class AttendeeDto(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String = "",
    val isGoing: Boolean = false,
    val remindAt: Long = 0L
)
