package me.androidbox.data.remote.model

import androidx.room.Entity
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.remote.model.request.EventRequestModel

@Entity(tableName = DatabaseConstant.AGENDA_TABLE)
data class AgendaModel(
    val listOfEvent: List<EventRequestModel>,
    val listOfTask: List<TaskModel>,
    val listOfReminder: List<ReminderModel>
)
