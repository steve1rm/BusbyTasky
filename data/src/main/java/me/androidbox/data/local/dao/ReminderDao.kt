package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.model.ReminderModel

@Dao
interface ReminderDao {
    @Query("SELECT * FROM ${DatabaseConstant.REMINDER_TABLE}")
    fun getReminder(): Flow<List<ReminderModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReminder(reminderModel: ReminderModel)

    @Update
    fun updateReminder(reminderModel: ReminderModel)

    @Delete
    fun deleteReminder(reminderModel: ReminderModel)
}