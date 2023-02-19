package me.androidbox.data.model.response

import me.androidbox.data.model.AttendeeModel
import me.androidbox.data.model.PhotoModel

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
