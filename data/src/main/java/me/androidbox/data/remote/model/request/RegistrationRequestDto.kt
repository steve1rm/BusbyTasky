package me.androidbox.data.remote.model.request

/**
 * /Register
 * POST
 * Registers a new user
 *
 * No Response body
 * */
data class RegistrationRequestDto(
    val fullName: String,
    val email: String,
    val password: String
)
