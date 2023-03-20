package me.androidbox.domain.authentication.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String, /* event creator ID */
    val isUserEventCreator: Boolean,
    val attendees: String,
    val photos: String,
)
