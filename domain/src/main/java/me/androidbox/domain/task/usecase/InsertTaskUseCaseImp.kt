package me.androidbox.domain.task.usecase

import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.task.repository.TaskRepository
import javax.inject.Inject

class InsertTaskUseCaseImp @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(task: Task): ResponseState<Unit> {
        return taskRepository.insertTask(task)
    }
}
