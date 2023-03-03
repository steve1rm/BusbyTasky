package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.AttendeeEntity

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM ${DatabaseConstant.ATTENDEE_TABLE}")
    fun getAttendee(): Flow<List<AttendeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttendee(attendeeModel: AttendeeEntity)

    @Delete
    fun deleteAttendee(attendeeModel: AttendeeEntity)

    @Query("DELETE FROM ${DatabaseConstant.ATTENDEE_TABLE}")
    fun deleteAllAttendee()
}