package me.androidbox.domain.authentication.model

data class Login(
    val token: String,
    val userId: String,
    val fullName: String
)
