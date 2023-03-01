package me.androidbox.data.remote.model

import me.androidbox.data.remote.model.request.EventRequestDto

data class AgendaDto(
    val listOfEvent: List<EventRequestDto>,
    val listOfTask: List<TaskDto>,
    val listOfReminder: List<ReminderDto>
)
