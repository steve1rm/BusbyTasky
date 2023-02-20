package me.androidbox.data.remote.model.response

import me.androidbox.data.remote.model.AttendeeModel
import me.androidbox.data.remote.model.PhotoModel

data class EventRequestModel(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String, /* event creator ID */
    val isUserEventCreator: Boolean,
    val listOfAttendee: List<AttendeeModel>,
    val listOfPhoto: List<PhotoModel>
)
