package me.androidbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.ATTENDEE_TABLE)
data class AttendeeModel(
/* TODO Can we use the key as a primary key as the email will be unique */
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)
