package me.androidbox.data.remote.network.event

import me.androidbox.data.remote.model.response.AttendeeEmailVerifyResponseDto
import me.androidbox.data.remote.model.response.EventDto
import me.androidbox.data.remote.network.EndPointConstant
import okhttp3.MultipartBody
import retrofit2.http.*

interface EventService {
    @Multipart
    @POST(EndPointConstant.EVENT)
    suspend fun createEvent(
        @Part listOfPhoto: List<MultipartBody.Part>,
        @Part eventBody: MultipartBody.Part
    ): EventDto

    @GET(EndPointConstant.EVENT)
    suspend fun getEventWithId(
        @Query("eventId") eventId: String
    ): EventDto

    @DELETE(EndPointConstant.EVENT)
    suspend fun deleteEventWithId(
        @Query("eventId") eventId: String
    )

    @Multipart
    @PUT(EndPointConstant.EVENT)
    suspend fun updateEvent(
        @Part listOfPhoto: List<MultipartBody.Part>,
        @Part eventBody: MultipartBody.Part
    ): EventDto

    @GET("/attendee")
    suspend fun verifyAttendee(
        @Query("email") email: String
    ): AttendeeEmailVerifyResponseDto
}