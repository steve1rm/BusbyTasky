package me.androidbox.data.remote.model.request


/**
 * /Reminder
 * POST
 * Creates a new reminder
 *
 * PUT
 * Updates an existing reminder
 *
 * No Response body
 * */
data class ReminderRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
