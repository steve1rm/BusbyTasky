package me.androidbox.data.remote.model

data class ReminderModel(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
