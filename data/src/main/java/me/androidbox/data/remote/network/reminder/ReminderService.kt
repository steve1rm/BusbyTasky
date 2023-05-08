package me.androidbox.data.remote.network.reminder

import me.androidbox.data.remote.model.response.ReminderDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReminderService {

    @POST("/reminder")
    suspend fun createReminder(
        @Body reminderDto: ReminderDto
    )

    @PUT("/reminder")
    suspend fun updateReminder(
        @Body reminderDto: ReminderDto
    )

    @GET("/reminder")
    suspend fun getReminder(
        @Query("reminderId") reminderId: String): ReminderDto

    @DELETE("/reminder")
    suspend fun deleteReminder(
        @Query("reminderId") reminderId: String
    )
}