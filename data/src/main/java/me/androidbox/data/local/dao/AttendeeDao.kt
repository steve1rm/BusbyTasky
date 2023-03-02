package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.remote.model.response.AttendeeDto

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM ${DatabaseConstant.ATTENDEE_TABLE}")
    fun getAttendee(): Flow<List<AttendeeDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttendee(attendeeModel: AttendeeDto)

    @Delete
    fun deleteAttendee(attendeeModel: AttendeeDto)
}