package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.TASK_TABLE)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)
