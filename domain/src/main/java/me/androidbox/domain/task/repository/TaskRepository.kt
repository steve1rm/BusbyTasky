package me.androidbox.domain.task.repository

import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.constant.SyncAgendaType

interface TaskRepository {
    suspend fun insertTask(task: Task): ResponseState<Unit>

    suspend fun deleteTaskById(taskId: String): ResponseState<Unit>

    fun getTaskById(taskId: String): Flow<ResponseState<Task>>

    suspend fun uploadTask(task: Task): ResponseState<Unit>

    suspend fun insertSyncTask(taskId: String): ResponseState<Unit>
}