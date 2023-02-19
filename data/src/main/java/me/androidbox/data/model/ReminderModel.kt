package me.androidbox.data.model

data class ReminderModel(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
