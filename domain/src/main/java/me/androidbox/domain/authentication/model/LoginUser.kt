package me.androidbox.domain.authentication.model

data class LoginUser(
    val token: String,
    val userId: String,
    val fullName: String
)
