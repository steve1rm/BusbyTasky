package me.androidbox.data.mapper

import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.remote.model.request.EventRequestDto
import me.androidbox.domain.authentication.model.Event

fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.startDateTime,
        endDateTime = this.endDateTime,
        remindAt = this.remindAt,
        eventCreatorId = this.eventCreatorId,
        isUserEventCreator = this.isUserEventCreator,
        attendees = this.attendees,
        photos = this.photos
    )
}

fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.startDateTime,
        endDateTime = this.endDateTime,
        remindAt = this.remindAt,
        eventCreatorId = this.eventCreatorId,
        isUserEventCreator = this.isUserEventCreator,
        attendees = this.attendees,
        photos = this.photos
    )
}

/*
val eventRequest = EventRequestDto(
    id = UUID.randomUUID().toString(),
    title = "Title",
    description = "description",
    from = 3L,
    to = 6L,
    remindAt = 4L,
    attendeeIds = listOf(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    )
)
*/

fun Event.toCreateEventDto(): EventRequestDto {
    return EventRequestDto(
        id = this.id,
        title = this.title,
        description = this.description,
        from = this.startDateTime,
        to = this.endDateTime,
        remindAt = this.remindAt,
        attendeeIds = this.attendees
    )
}

fun Event.toUpdateEventDto(): EventRequestDto {
    return EventRequestDto(

    )
}
