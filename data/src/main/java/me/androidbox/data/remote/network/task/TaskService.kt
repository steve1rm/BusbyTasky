package me.androidbox.data.remote.network.task

import me.androidbox.data.remote.model.response.TaskDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskService {

    @POST("/task")
    suspend fun createTask(
        @Body taskDto: TaskDto
    )

    @PUT("/task")
    suspend fun updateTask(
        @Body taskDto: TaskDto
    )

    @GET("/task")
    suspend fun getTask(
        @Query("taskId") taskId: String): TaskDto

    @DELETE("/task")
    suspend fun deleteTask(
        @Query("taskId") taskId: String
    )
}
