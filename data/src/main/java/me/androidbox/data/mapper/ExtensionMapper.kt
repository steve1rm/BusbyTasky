package me.androidbox.data.mapper

import me.androidbox.data.local.entity.EventEntity
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