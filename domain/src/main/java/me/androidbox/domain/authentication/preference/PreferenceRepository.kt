package me.androidbox.domain.authentication.preference

import me.androidbox.domain.authentication.model.LoginUser

interface PreferenceRepository {
    fun saveToken(token: String)
    fun saveUserId(userId: String)
    fun saveFullName(fullName: String)

    fun retrieveCurrentUserOrNull(): LoginUser?

    fun deleteCurrentUser()
}
