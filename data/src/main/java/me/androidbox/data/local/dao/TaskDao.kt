package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.remote.model.TaskModel

@Dao
interface TaskDao {
    @Query("SELECT * FROM ${DatabaseConstant.TASK_TABLE}")
    fun getTask(): Flow<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(taskModel: TaskModel)

    @Update
    fun updateTask(taskModel: TaskModel)

    @Delete
    fun deleteTask(taskModel: TaskModel)
}