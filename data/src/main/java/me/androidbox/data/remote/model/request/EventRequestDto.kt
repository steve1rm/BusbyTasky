package me.androidbox.data.remote.model.request


/**
 * /event
 * POST/Multipart
 *
 * Creates a new event. Since Tasky supports offline mode, the IDs for agenda items
 * need to be generated client-side. Please use UUID.randomUUID().toString() for that.
 *
 * Response body: EventDto
 * */
data class EventRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val attendeeIds: List<String>
)
