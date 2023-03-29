package me.androidbox.data.remote.model.response

/**
 * /reminder
 * GET
 * Gets an existing reminder by ID
 * Query parameters `reminderId` The ID of the reminder
 *
 * Response body `ReminderDto`
 * */
data class ReminderDto(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
