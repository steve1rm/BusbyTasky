package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE} WHERE `from` >= :startTimeStamp AND `from` <= :endTimeStamp")
    fun getEventFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(eventEntity: EventEntity)

    /** TODO
     * Delete only a single event and all the attendees and photos that have been added to that event
     * */
    @Delete
    fun deleteEvent(eventEntity: EventEntity)

    /* TODO Maybe there is a use case when the user want to clear all events */
    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE}")
    fun deleteAllEvent()
}