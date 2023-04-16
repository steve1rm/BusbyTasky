package me.androidbox.data.mapper

import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.local.entity.ReminderEntity
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.remote.model.request.EventCreateRequestDto
import me.androidbox.data.remote.model.request.EventUpdateRequestDto
import me.androidbox.data.remote.model.request.SyncAgendaDto
import me.androidbox.data.remote.model.response.AttendeeDto
import me.androidbox.data.remote.model.response.EventDto
import me.androidbox.data.remote.model.response.ReminderDto
import me.androidbox.data.remote.model.response.TaskDto
import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.Reminder
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.agenda.model.SyncAgenda
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
        isGoing = this.isGoing,
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
        isGoing = true,
        attendees = this.attendees.map { it.toAttendee() },
        photos = this.photos.map { it.key }
    )
}

fun TaskDto.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone
    )
}

fun ReminderDto.toReminder(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.time,
        remindAt = this.remindAt,
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
        isGoing = this.isGoing,
        attendees = this.attendees,
        photos = this.photos
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.startDateTime,
        remindAt = this.remindAt,
        isDone = this.isDone
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone
    )
}

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        time = this.startDateTime
    )
}

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        startDateTime = this.time
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
        isGoing = this.isGoing
    )
}

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        email = this.email,
        fullName = this.fullName,
        userId = this.userId,
        eventId = "",
        isGoing = false,
        remindAt = 0L)
}

fun SyncAgenda.toSyncAgendaDto(): SyncAgendaDto {
    return SyncAgendaDto(
        deletedEventIds = this.deletedEventIds,
        deletedTaskIds = this.deletedTaskIds,
        deletedReminderIds = this.deletedReminderIds
    )
}