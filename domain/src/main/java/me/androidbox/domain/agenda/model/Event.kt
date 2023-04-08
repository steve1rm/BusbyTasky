package me.androidbox.domain.agenda.model

data class Event(
    override val id: String,
    override val title: String,
    override val description: String,
    override val startDateTime: Long,
    override val remindAt: Long,
    val endDateTime: Long,
    val eventCreatorId: String,
    val isUserEventCreator: Boolean,
    val attendees: List<Attendee>,
    val photos: List<String>,
) : AgendaItem(
    id = id,
    title = title,
    description = description,
    startDateTime = startDateTime,
    remindAt = remindAt
)
