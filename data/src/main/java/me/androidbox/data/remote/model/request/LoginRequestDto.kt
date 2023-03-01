package me.androidbox.data.remote.model.request

/**
 * /Login
 * POST
 * Logs in a existing user
 *
 * Response body: LoginDto
 * */
data class LoginRequestDto(
    val email: String,
    val password: String
)
