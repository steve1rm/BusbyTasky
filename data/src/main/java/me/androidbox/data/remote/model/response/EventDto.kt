package me.androidbox.data.remote.model.response

/**
 * /event
 * GET
 * Returns an existing event
 * Query parameters `eventId` The ID of the event
 *
 * Response body `EventDto`
 * */

data class EventDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long = 0L,
    val host: String,
    val isUserEventCreator: Boolean = false,
    val attendees: List<AttendeeDto> = emptyList(),
    val photos: List<PhotoDto> = emptyList(),
    val attendeeIds: List<String> = emptyList()
)
