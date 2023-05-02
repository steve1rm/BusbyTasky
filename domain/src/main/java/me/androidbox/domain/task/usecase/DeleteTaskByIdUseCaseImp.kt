package me.androidbox.domain.task.usecase

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.task.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskByIdUseCaseImp @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): ResponseState<Unit> {
        return taskRepository.deleteTaskById(taskId)
    }
}