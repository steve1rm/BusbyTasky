package me.androidbox.data.remote.model.response

/**
 * /task
 * GET
 * Gets an existing task by ID
 * Query parameters `taskId` The Task ID
 *
 * Response body: TaskDto
 * */
data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)