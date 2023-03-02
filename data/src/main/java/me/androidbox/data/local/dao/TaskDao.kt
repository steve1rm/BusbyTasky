package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE}")
    fun getTask(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Delete
    fun deleteTask(taskEntity: TaskEntity)
}