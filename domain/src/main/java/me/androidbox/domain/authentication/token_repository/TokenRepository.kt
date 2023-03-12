package me.androidbox.domain.authentication.token_repository

interface TokenRepository {
    suspend fun saveUserToken(key: String, token: String)
    suspend fun retrieveUserToken(key: String): String?
}
