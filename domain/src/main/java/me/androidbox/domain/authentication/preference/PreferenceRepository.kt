package me.androidbox.domain.authentication.preference

import me.androidbox.domain.authentication.model.LoginUser

interface PreferenceRepository {
    fun retrieveCurrentUserOrNull(): LoginUser?
    fun saveCurrentUser(loginUser: LoginUser)
    fun deleteCurrentUser()
}
