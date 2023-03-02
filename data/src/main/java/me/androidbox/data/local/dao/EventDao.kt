package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE}")
    fun getEvent(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(eventEntity: EventEntity)

    @Update
    fun updateEvent(eventEntity: EventEntity)

    @Delete
    fun deleteEvent(eventEntity: EventEntity)
}