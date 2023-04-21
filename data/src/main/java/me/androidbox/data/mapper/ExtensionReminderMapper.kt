package me.androidbox.data.mapper

import me.androidbox.data.local.entity.ReminderEntity
import me.androidbox.data.remote.model.response.ReminderDto
import me.androidbox.domain.agenda.model.Reminder


fun ReminderDto.toReminder(): Reminder {
    return Reminder(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.time,
        remindAt = this.remindAt,
    )
}

fun ReminderDto.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.time,
        remindAt = this.remindAt,
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
