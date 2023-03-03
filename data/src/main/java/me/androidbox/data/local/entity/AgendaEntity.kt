package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.AGENDA_TABLE)
data class AgendaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventId: String,   /* Foreign key */
    val taskId: String,    /* Foreign key */
    val reminderId: String /* Foreign key */
)
