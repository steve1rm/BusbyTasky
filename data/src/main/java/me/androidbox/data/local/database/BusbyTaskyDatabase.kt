package me.androidbox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.data.local.converter.AttendeeConverter
import me.androidbox.data.local.converter.PhotoConverter
import me.androidbox.data.local.dao.*
import me.androidbox.data.local.entity.*

@Database(entities = [
  //  AttendeeEntity::class,
    EventEntity::class,
    PhotoEntity::class,
    ReminderEntity::class,
    TaskEntity::class],
    version = 1
)
@TypeConverters(AttendeeConverter::class, PhotoConverter::class)
abstract class BusbyTaskyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun eventDao(): EventDao

    abstract fun reminderDao(): ReminderDao

    abstract fun photoDao(): PhotoDao
}
