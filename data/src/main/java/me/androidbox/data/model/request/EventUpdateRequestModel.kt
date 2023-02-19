package me.androidbox.data.model.request

data class EventUpdateRequestModel(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val listOfAttendee: List<String>, /* attendee id */
    val listOfDeletedPhotoKey: List<String>,
    val isGoing: Boolean
)

