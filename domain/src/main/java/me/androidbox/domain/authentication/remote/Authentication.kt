package me.androidbox.domain.authentication.remote

import me.androidbox.domain.authentication.model.LoginModel

interface Authentication {
    suspend fun registerUser(fullName: String, email: String, password: String)
    suspend fun loginUser(email: String, password: String): LoginModel
}