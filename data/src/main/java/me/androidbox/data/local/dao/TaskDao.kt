package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.TaskEntity
import me.androidbox.domain.constant.SyncAgendaType

@Dao
interface TaskDao {
    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE time >= :startTimeStamp AND time <= :endTimeStamp")
    fun getTasksFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    /** TODO
     * Delete only a single task
     * */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE} WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all tasks */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE}")
    suspend fun deleteAllTask()

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllDeletedTasks(syncAgendaType: SyncAgendaType): List<String>

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllCreatedTasks(syncAgendaType: SyncAgendaType): List<String>

    @Query("SELECT `id` FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `syncAgendaType` = :syncAgendaType")
    suspend fun getAllUpdatedTasks(syncAgendaType: SyncAgendaType): List<String>

    @Query("DELETE FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `id` = :id")
    suspend fun deleteSyncTaskById(id: String)

    @Query("DELETE FROM ${DatabaseConstant.TASK_SYNC_TABLE} WHERE `id` IN :ids")
    suspend fun deleteAllSyncTasksByIds(ids: List<String>)
}