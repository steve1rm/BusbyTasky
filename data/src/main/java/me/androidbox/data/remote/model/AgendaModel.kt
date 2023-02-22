package me.androidbox.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.AGENDA_TABLE)
data class AgendaModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
/*
    val listOfEvent: List<EventRequestModel>,
    val listOfTask: List<TaskModel>,
    val listOfReminder: List<ReminderModel>
*/
)
