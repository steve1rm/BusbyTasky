package me.androidbox.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.data.local.converter.AttendeeConverter
import me.androidbox.data.local.converter.GenericListConvertor
import me.androidbox.data.local.converter.PhotoConverter
import me.androidbox.data.local.dao.*
import me.androidbox.data.local.entity.*

@Database(entities = [
    EventEntity::class,
    ReminderEntity::class,
    TaskEntity::class],
    version = 1
)
@TypeConverters(AttendeeConverter::class, PhotoConverter::class, GenericListConvertor::class)
abstract class BusbyTaskyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun eventDao(): EventDao

    abstract fun reminderDao(): ReminderDao
}
