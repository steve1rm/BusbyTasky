package me.androidbox.domain.authentication.preference

interface PreferenceRepository {
    suspend fun saveToken(token: String)
    suspend fun saveUserId(userId: String)
    suspend fun saveFullName(fullName: String)

    suspend fun retrieveCurrentUser()
}
