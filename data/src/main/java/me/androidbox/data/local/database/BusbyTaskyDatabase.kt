package me.androidbox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.androidbox.data.local.dao.*
import me.androidbox.data.remote.model.*

@Database(entities = [
    AgendaModel::class,
    AttendeeModel::class,
    EventModel::class,
    PhotoModel::class,
    ReminderModel::class,
    TaskModel::class],
    version = 1
)
abstract class BusbyTaskyDatabase : RoomDatabase() {
    abstract fun agendaDao(): AgendaDao

    abstract fun taskDao(): TaskDao

    abstract fun eventDao(): EventDao

    abstract fun reminderDao(): ReminderDao

    abstract fun photoDao(): PhotoDao

    abstract fun attendeeDao(): AttendeeDao
}
