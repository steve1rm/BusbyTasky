package me.androidbox.domain.authentication.remote

import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser

interface AuthenticationRepository {
    suspend fun registerUser(fullName: String, email: String, password: String): ResponseState<Unit>
    suspend fun loginUser(email: String, password: String): ResponseState<AuthenticatedUser>
}