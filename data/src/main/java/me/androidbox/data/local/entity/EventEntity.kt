package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.domain.agenda.model.Attendee

@Entity(tableName = DatabaseConstant.EVENT_TABLE)
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val startDateTime: Long,
    val endDateTime: Long,
    val remindAt: Long,
    val eventCreatorId: String,
    val isUserEventCreator: Boolean,
    val isGoing: Boolean,
    val attendees: List<Attendee>,
    val photos: List<String>,
)
