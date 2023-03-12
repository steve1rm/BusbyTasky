package me.androidbox.domain.authentication.token_repository

interface PreferenceRepository {
    suspend fun saveToken(token: String)
    suspend fun saveUserId(userId: String)
    suspend fun saveFullName(fullName: String)

    suspend fun retrieveCurrentUser()
}
