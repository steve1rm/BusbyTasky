package me.androidbox.domain.task.repository

import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState

interface TaskRepository {
    suspend fun insertTask(task: Task): ResponseState<Unit>

    suspend fun deleteTaskById(taskId: String): ResponseState<Unit>

    suspend fun getTaskById(taskId: String): ResponseState<Task>
}