package me.androidbox.data.mapper

import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.remote.model.response.TaskDto
import me.androidbox.domain.agenda.model.Task


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

fun TaskDto.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone
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

fun Task.toTaskDto(): TaskDto {
    return TaskDto(
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
