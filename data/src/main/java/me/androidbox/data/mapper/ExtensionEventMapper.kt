package me.androidbox.data.mapper

import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.remote.model.request.EventCreateRequestDto
import me.androidbox.data.remote.model.request.EventUpdateRequestDto
import me.androidbox.data.remote.model.request.SyncAgendaDto
import me.androidbox.data.remote.model.response.AttendeeDto
import me.androidbox.data.remote.model.response.EventDto
import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.SyncAgenda
import java.util.UUID

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
        host = this.host,
        attendees = this.attendees,
        photos = this.photos
    )
}

fun EventDto.toEvent(): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.from,
        endDateTime = this.to,
        remindAt = this.remindAt,
        eventCreatorId = this.host,
        isUserEventCreator = this.isUserEventCreator,
        host = this.host,
        attendees = this.attendees.map { it.toAttendee() },
        photos = this.photos.map { it.key }
    )
}

fun EventDto.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.from,
        endDateTime = this.to,
        remindAt = this.remindAt,
        eventCreatorId = this.host,
        isUserEventCreator = this.isUserEventCreator,
        host = this.host,
        attendees = this.attendees.map { attendeeDto -> attendeeDto.toAttendee() },
        photos = this.photos.map { photoDto -> photoDto.key }
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
        host = this.host,
        attendees = this.attendees,
        photos = this.photos
    )
}

fun Event.toCreateEventDto(): EventCreateRequestDto {
    return EventCreateRequestDto(
        id = this.id,
        title = this.title,
        description = this.description,
        from = this.startDateTime,
        to = this.endDateTime,
        remindAt = this.remindAt,
        attendeeIds = this.attendees.map { attendee -> attendee.userId }
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
        attendeeIds = this.attendees.map { attendee -> attendee.userId },
        deletedPhotoKeys = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()), /** TODO These keys are obtained when uploading a created event and the BE will return the key and the url */
        isGoing = true
    )
}

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        email = this.email,
        fullName = this.fullName,
        userId = this.userId,
        eventId = this.eventId,
        isGoing = this.isGoing ,
        remindAt = this.remindAt)
}

fun SyncAgenda.toSyncAgendaDto(): SyncAgendaDto {
    return SyncAgendaDto(
        deletedEventIds = this.deletedEventIds,
        deletedTaskIds = this.deletedTaskIds,
        deletedReminderIds = this.deletedReminderIds
    )
}