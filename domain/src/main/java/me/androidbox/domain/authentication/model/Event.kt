package me.androidbox.domain.authentication.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startDateTime: Long = 0L,
    val endDateTime: Long = 0L,
    val remindAt: Long = 0L,
    val eventCreatorId: String = "",
    val isUserEventCreator: Boolean = false,
    val attendees: String = "",
    val photos: String = "",
)
