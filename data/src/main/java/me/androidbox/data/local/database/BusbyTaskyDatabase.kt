package me.androidbox.data.local.database

import androidx.room.Database
import me.androidbox.data.local.dao.AgendaDao
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.model.*

@Database(entities = [
    Agenda::class,
    AttendeeModel::class,
    EventModel::class,
    PhotoModel::class,
    ReminderModel::class,
    TaskModel::class],
    version = 1
)
interface BusbyTaskyDatabase {
    fun agendaDao(): AgendaDao

    fun taskDao(): TaskDao

    fun eventDao(): EventDao
}
