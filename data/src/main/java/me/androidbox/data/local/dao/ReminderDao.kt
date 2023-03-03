package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM ${DatabaseConstant.REMINDER_TABLE}")
    fun getReminder(): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminderEntity: ReminderEntity)

    @Delete
    fun deleteReminder(reminderEntity: ReminderEntity)

    @Query("DELETE FROM ${DatabaseConstant.REMINDER_TABLE}")
    fun deleteAllReminder()
}