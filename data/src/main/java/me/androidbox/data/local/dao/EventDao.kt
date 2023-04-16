package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.local.entity.EventSyncEntity
import me.androidbox.domain.constant.SyncAgendaType

@Dao
interface EventDao {
    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE} WHERE `startDateTime` >= :startTimeStamp AND `startDateTime` <= :endTimeStamp")
    fun getEventsFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<EventEntity>>

    @Query("SELECT * FROM ${DatabaseConstant.EVENT_TABLE} WHERE `id` == :id")
    fun getEventById(id: String): Flow<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)

    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE} WHERE id = :id")
    suspend fun deleteEventById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all events */
    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE}")
    suspend fun deleteAllEvent()

    @Upsert(entity = EventSyncEntity::class)
    suspend fun insertSyncEvent(eventSyncEntity: EventSyncEntity)

    @Query("SELECT * FROM ${DatabaseConstant.EVENT_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllDeletedEvents(syncAgendaType: SyncAgendaType)

    @Query("SELECT * FROM ${DatabaseConstant.EVENT_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllCreatedEvents(syncAgendaType: SyncAgendaType)

    @Query("SELECT * FROM ${DatabaseConstant.EVENT_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllDeletedUpdated(syncAgendaType: SyncAgendaType)

    @Query("DELETE FROM ${DatabaseConstant.EVENT_TABLE} WHERE id = :id")
    suspend fun deleteSyncEventById(id: String)

}