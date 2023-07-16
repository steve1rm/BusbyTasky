package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.data.local.entity.TaskSyncEntity
import me.androidbox.domain.constant.SyncAgendaType

@Dao
interface TaskDao {
    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE time >= :startTimeStamp AND time <= :endTimeStamp")
    fun getTasksFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE time >= :startTimeStamp AND time <= :endTimeStamp")
    suspend fun getTasksFromTimeStampFullAgenda(startTimeStamp: Long, endTimeStamp: Long): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Upsert(entity = TaskEntity::class)
    suspend fun insertTasks(taskEntities: List<TaskEntity>)

    @Upsert(entity = TaskSyncEntity::class)
    suspend fun insertSyncEvent(taskSyncEntity: TaskSyncEntity)

    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity

    /** TODO
     * Delete only a single task
     * */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE} WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all tasks */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE}")
    suspend fun deleteAllTask()

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllTasksBySyncType(syncAgendaType: SyncAgendaType): List<String>

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllCreatedTasks(syncAgendaType: SyncAgendaType): List<String>

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllUpdatedTasks(syncAgendaType: SyncAgendaType): List<String>

    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE remindAt > :startDateTime")
    suspend fun getAllRemindAt(startDateTime: Long): List<TaskEntity>

    @Query("DELETE FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `id` = :id")
    suspend fun deleteSyncTaskById(id: String)

    @Query("DELETE FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun deleteSyncTasksBySyncType(syncAgendaType: SyncAgendaType)

    @Query("DELETE FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `id` IN (:ids)")
    suspend fun deleteAllSyncTasksByIds(ids: List<String>)
}