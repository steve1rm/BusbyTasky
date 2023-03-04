package me.androidbox.data.local.entity

data class Attendee(
    val id: Int,
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)
