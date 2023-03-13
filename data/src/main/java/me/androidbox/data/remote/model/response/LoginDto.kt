package me.androidbox.data.remote.model.response

data class LoginDto(
    val token: String,
    val userId: String,
    val fullName: String
)
