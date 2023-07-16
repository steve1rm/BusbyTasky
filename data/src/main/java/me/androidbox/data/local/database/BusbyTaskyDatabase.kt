package me.androidbox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.data.local.converter.AttendeeConverter
import me.androidbox.data.local.converter.PhotoConverter
import me.androidbox.data.local.converter.StringListConvertor
import me.androidbox.data.local.dao.EventDao
import me.androidbox.data.local.dao.ReminderDao
import me.androidbox.data.local.dao.TaskDao
import me.androidbox.data.local.entity.*

@Database(entities = [
    EventEntity::class,
    ReminderEntity::class,
    TaskEntity::class,
    EventSyncEntity::class,
    TaskSyncEntity::class,
    ReminderSyncEntity::class],
    version = 1
)
@TypeConverters(AttendeeConverter::class, PhotoConverter::class, StringListConvertor::class)
abstract class BusbyTaskyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun eventDao(): EventDao

    abstract fun reminderDao(): ReminderDao
}
