package me.androidbox.domain.authentication.remote

import me.androidbox.domain.authentication.NetworkResponseState
import me.androidbox.domain.authentication.model.Login

interface AuthenticationRepository {
    suspend fun registerUser(fullName: String, email: String, password: String): NetworkResponseState<Unit>
    suspend fun loginUser(email: String, password: String): NetworkResponseState<Login>
}