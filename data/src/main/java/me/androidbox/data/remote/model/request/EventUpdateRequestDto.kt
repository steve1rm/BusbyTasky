package me.androidbox.data.remote.model.request


/**
 * /event
 * PUT/Multipart
 *
 * Updates an existing event. In addition to creating an event, this also takes
 * potentially deleted photos into consideration. Also, the isGoing boolean refers to the isGoing
 * status of the local user.
 *
 * Response body: EventDto
 * */
data class EventUpdateRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val attendeeIds: List<String>, /* attendee id */
    val deletedPhotoKeys: List<String>,
    val isGoing: Boolean
)

