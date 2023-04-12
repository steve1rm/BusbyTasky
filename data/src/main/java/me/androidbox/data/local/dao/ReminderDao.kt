package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM ${DatabaseConstant.REMINDER_TABLE} WHERE time >= :startTimeStamp AND time <= :endTimeStamp")
    fun getRemindersFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminderEntity: ReminderEntity)

    /**
     * TODO Delete only a single reminder
     * */
    @Query("DELETE FROM ${DatabaseConstant.REMINDER_TABLE} WHERE id = :id")
    suspend fun deleteReminderById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all reminders */
    @Query("DELETE FROM ${DatabaseConstant.REMINDER_TABLE}")
    suspend fun deleteAllReminder()
}