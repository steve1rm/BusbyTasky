package me.androidbox.data.remote.model.request

data class LoginResponseModel(
    val token: String,
    val userId: String,
    val fullName: String
)
