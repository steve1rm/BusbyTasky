package me.androidbox.domain.authentication.preference

import me.androidbox.domain.authentication.model.LoginUser

interface PreferenceRepository {
    suspend fun saveToken(token: String)
    suspend fun saveUserId(userId: String)
    suspend fun saveFullName(fullName: String)

    suspend fun retrieveCurrentUserOrNull(): LoginUser?

    suspend fun deleteCurrentUser()
}
