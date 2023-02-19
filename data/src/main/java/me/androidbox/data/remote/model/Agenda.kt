package me.androidbox.data.remote.model

import me.androidbox.data.remote.model.request.EventRequestModel

data class Agenda(
    val listOfEvent: List<EventRequestModel>,
    val listOfTask: List<TaskModel>,
    val listOfReminder: List<ReminderModel>
)
