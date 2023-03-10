package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE} WHERE time >= :startTimeStamp AND time <= :endTimeStamp")
    fun getTasksFromTimeStamp(startTimeStamp: Long, endTimeStamp: Long): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    /** TODO
     * Delete only a single task
     * */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE} WHERE id = :id")
    fun deleteTaskById(id: String)

    /* TODO Maybe there is a use case when the user want to clear all tasks */
    @Query("DELETE FROM ${DatabaseConstant.TASK_TABLE}")
    fun deleteAllTask()
}