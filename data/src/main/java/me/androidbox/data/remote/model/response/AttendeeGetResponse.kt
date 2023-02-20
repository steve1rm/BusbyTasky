package me.androidbox.data.remote.model.response

import me.androidbox.data.remote.model.AttendeeModel

data class AttendeeGetResponse(
    val isExistingUser: Boolean,
    val attendee: AttendeeModel
)
