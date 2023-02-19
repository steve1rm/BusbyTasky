package me.androidbox.data.model

import me.androidbox.data.model.request.EventRequestModel

data class Agenda(
    val listOfEvent: List<EventRequestModel>,
    val listOfTask: List<TaskModel>,
    val listOfReminder: List<ReminderModel>
)
