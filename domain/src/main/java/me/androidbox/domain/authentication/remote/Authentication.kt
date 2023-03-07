package me.androidbox.domain.authentication.remote

interface Authentication {
    suspend fun registerUser(fullName: String, email: String, password: String)
}