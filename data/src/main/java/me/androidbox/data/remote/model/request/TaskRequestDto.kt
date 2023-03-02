package me.androidbox.data.remote.model.request


/**
 * /task
 * POST
 * Creates a new task
 *
 * PUT
 * Updates an existing task
 *
 * No Response body
 *
 * */
data class TaskRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)
