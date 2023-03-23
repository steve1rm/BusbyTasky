package me.androidbox.data.remote.network.event

import me.androidbox.data.remote.model.request.EventRequestDto
import me.androidbox.data.remote.model.request.EventUpdateRequestDto
import me.androidbox.data.remote.model.response.EventDto
import me.androidbox.data.remote.network.EndPointConstant
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface EventService {
    @Multipart
    @POST(EndPointConstant.EVENT)
    suspend fun createEvent(
        @Body eventRequestDto: EventRequestDto
    ): EventDto

    @GET(EndPointConstant.EVENT)
    suspend fun getEventWithId(
        @Query("id") eventId: String
    ): EventDto

    @DELETE(EndPointConstant.EVENT)
    suspend fun deleteEventWithId(
        @Query("id") eventId: String
    )

    @Multipart
    @PUT(EndPointConstant.EVENT)
    suspend fun updateEvent(
        @Body eventUpdateRequestDto: EventUpdateRequestDto
    ): EventDto
}