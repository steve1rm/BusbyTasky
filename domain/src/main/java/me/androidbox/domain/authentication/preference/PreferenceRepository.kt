package me.androidbox.domain.authentication.preference

import me.androidbox.domain.authentication.model.AuthenticatedUser

interface PreferenceRepository {
    fun retrieveCurrentUserOrNull(): AuthenticatedUser?
    fun saveCurrentUser(authenticatedUser: AuthenticatedUser)
    fun deleteCurrentUser()
}
