package me.androidbox.domain.authentication.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startDateTime: Long,
    val endDateTime: Long,
    val remindAt: Long,
    val eventCreatorId: String,
    val isUserEventCreator: Boolean,
    val attendees: String,
    val photos: String,
)
