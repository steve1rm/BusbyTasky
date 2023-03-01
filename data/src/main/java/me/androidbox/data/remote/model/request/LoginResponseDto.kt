package me.androidbox.data.remote.model.request

data class LoginResponseDto(
    val token: String,
    val userId: String,
    val fullName: String
)
