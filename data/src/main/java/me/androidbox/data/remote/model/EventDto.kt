package me.androidbox.data.remote.model

data class EventDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long
)
