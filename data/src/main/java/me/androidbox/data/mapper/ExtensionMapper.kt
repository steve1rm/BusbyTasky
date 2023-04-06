package me.androidbox.data.mapper

import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.remote.model.request.EventRequestDto
import me.androidbox.data.remote.model.request.EventUpdateRequestDto
import me.androidbox.domain.authentication.model.Event
import java.util.*

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
        isGoing = true,
        attendees = listOf(), //this.attendees,
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
        attendees = "", /* this will be the json string return from the db this.attendees */
        photos = this.photos
    )
}

fun Event.toCreateEventDto(): EventRequestDto {
    return EventRequestDto(
        id = this.id,
        title = this.title,
        description = this.description,
        from = this.startDateTime,
        to = this.endDateTime,
        remindAt = this.remindAt,
        attendeeIds = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString()) /**  event will have a list of Attendee i.e. listOfAttendee.map { it.userId } */
    )
}

fun Event.toUpdateEventDto(): EventUpdateRequestDto {
    return EventUpdateRequestDto(
        id = this.id,
        title = this.title,
        description = this.description,
        from = this.startDateTime,
        to = this.endDateTime,
        remindAt = this.remindAt,
        attendeeIds = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString()), /** TODO event will have a list of Attendee i.e. listOfAttendee.map { it.userId } */
        deletedPhotoKeys = listOf("key1", "key2", "key3"), /** TODO These keys are obtained when uploading a created event and the BE will return the key and the url */
        isGoing = this.isGoing
    )
}
