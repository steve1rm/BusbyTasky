package me.androidbox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.androidbox.data.local.dao.*
import me.androidbox.data.local.entity.*

@Database(entities = [
    AttendeeEntity::class,
    EventEntity::class,
    PhotoEntity::class,
    ReminderEntity::class,
    TaskEntity::class],
    version = 1
)
abstract class BusbyTaskyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun eventDao(): EventDao

    abstract fun reminderDao(): ReminderDao

    abstract fun photoDao(): PhotoDao

    abstract fun attendeeDao(): AttendeeDao
}
