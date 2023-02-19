package me.androidbox.data.model

import androidx.room.Entity
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.model.request.EventRequestModel

@Entity(tableName = DatabaseConstant.AGENDA_TABLE)
data class Agenda(
    val listOfEvent: List<EventRequestModel>,
    val listOfTask: List<TaskModel>,
    val listOfReminder: List<ReminderModel>
)
