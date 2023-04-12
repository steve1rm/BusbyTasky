package me.androidbox.data.remote.model.response

/**
 * /attendee
 * GET
 * Checks if a user with a given email exists and can be added as attendee to an event.
 *
 * Query parameters: email
 * Response: GetAttendeeDto
 * */
data class AttendeeEmailVerifyResponseDto(
    val doesUserExist: Boolean,
    val attendee: AttendeeDto
)
