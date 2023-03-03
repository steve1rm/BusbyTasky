package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.REMINDER_TABLE)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)
