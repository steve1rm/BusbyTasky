package me.androidbox.domain.agenda.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startDateTime: Long,
    val endDateTime: Long,
    val remindAt: Long,
    val eventCreatorId: String,
    val isUserEventCreator: Boolean,
    val attendees: List<Attendee>,
    val photos: List<String>,
)
