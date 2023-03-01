package me.androidbox.data.remote.model.request

data class EventRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val listOfAttendee: List<String>
)
