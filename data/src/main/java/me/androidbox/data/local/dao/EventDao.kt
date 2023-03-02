package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.EventModel

@Dao
interface EventDao {
    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE}")
    fun getEvent(): Flow<List<EventModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(eventModel: EventModel)

    @Update
    fun updateEvent(eventModel: EventModel)

    @Delete
    fun deleteEvent(eventModel: EventModel)
}