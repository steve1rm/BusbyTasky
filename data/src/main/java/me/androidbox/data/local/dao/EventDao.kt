package me.androidbox.data.local.dao

import androidx.room.*
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE} WHERE `startDateTime` >= :startTimeStamp AND `startDateTime` <= :endTimeStamp")
    suspend fun getEventsFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)

    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE} WHERE id = :id")
    suspend fun deleteEventById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all events */
    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE}")
    suspend fun deleteAllEvent()
}