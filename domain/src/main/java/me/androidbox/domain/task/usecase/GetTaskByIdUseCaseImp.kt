package me.androidbox.domain.task.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.task.repository.TaskRepository

class GetTaskByIdUseCaseImp @Inject constructor(
    private val taskRepository: TaskRepository
) {
    fun execute(taskId: String): Flow<ResponseState<Task>> {
        return taskRepository.getTaskById(taskId)
    }
}
