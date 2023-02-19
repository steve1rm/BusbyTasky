package me.androidbox.data.model.response

import me.androidbox.data.model.AttendeeModel

data class AttendeeGetResponse(
    val isExistingUser: Boolean,
    val attendee: AttendeeModel
)
