package me.androidbox.domain.authentication.model

data class AuthenticatedUser(
    val token: String,
    val userId: String,
    val fullName: String
)
