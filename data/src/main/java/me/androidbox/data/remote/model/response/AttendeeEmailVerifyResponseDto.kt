package me.androidbox.data.remote.model.response

import com.squareup.moshi.Json

data class AttendeeEmailVerifyResponseDto(
    val doesUserExist: Boolean,
    @Json(name = "attendee")
    val attendeeVerifyDto: AttendeeVerifyDto
)
