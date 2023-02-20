package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.model.AttendeeModel

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM ${DatabaseConstant.ATTENDEE_TABLE}")
    fun getAttendee(): Flow<List<AttendeeModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttendee(attendeeModel: AttendeeModel)

    @Update
    fun updateAttendee(attendeeModel: AttendeeModel)

    @Delete
    fun deleteAttendee(attendeeModel: AttendeeModel)
}