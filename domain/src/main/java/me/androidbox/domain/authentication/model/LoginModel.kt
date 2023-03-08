package me.androidbox.domain.authentication.model

data class LoginModel(
    val token: String,
    val userId: String,
    val fullName: String
)
