package me.androidbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.REMINDER_TABLE)
data class ReminderModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
