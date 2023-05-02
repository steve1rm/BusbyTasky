package me.androidbox.domain.task.usecase

import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.task.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCaseImp @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): ResponseState<Task> {
        return taskRepository.getTaskById(taskId)
    }
}
