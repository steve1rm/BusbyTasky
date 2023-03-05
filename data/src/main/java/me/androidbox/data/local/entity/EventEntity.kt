package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.EVENT_TABLE)
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String, /* event creator ID */
    val isUserEventCreator: Boolean,

    val attendees: String,
    val photos: String
)
