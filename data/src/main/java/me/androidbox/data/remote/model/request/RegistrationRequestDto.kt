package me.androidbox.data.remote.model.request

data class RegistrationRequestDto(
    val fullName: String,
    val email: String,
    val password: String
)
