package me.androidbox.data.remote.preference

import android.content.SharedPreferences
import javax.inject.Inject
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.preference.PreferenceRepository

class PreferenceRepositoryImp @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PreferenceRepository {

    companion object {
        const val TOKEN_KEY = "token_key"
        const val USER_ID_KEY = "user_id_key"
        const val FULL_NAME_KEY = "full_name"
    }

    override fun saveCurrentUser(authenticatedUser: AuthenticatedUser) {
        sharedPreferences
            .edit()
            .putString(TOKEN_KEY, authenticatedUser.token)
            .putString(USER_ID_KEY, authenticatedUser.userId)
            .putString(FULL_NAME_KEY, authenticatedUser.fullName)
            .apply()
    }

    override fun retrieveCurrentUserOrNull(): AuthenticatedUser? {
        val token = sharedPreferences.getString(TOKEN_KEY, "")
        val userId = sharedPreferences.getString(USER_ID_KEY, "")
        val fullName = sharedPreferences.getString(FULL_NAME_KEY, "")

        return if (!token.isNullOrEmpty() && !userId.isNullOrEmpty() && !fullName.isNullOrEmpty()) {
            AuthenticatedUser(
                token = token,
                userId = userId,
                fullName = fullName
            )
        } else {
            null
        }
    }

    override fun deleteCurrentUser() {
        sharedPreferences
            .edit()
            .remove(TOKEN_KEY)
            .remove(USER_ID_KEY)
            .remove(FULL_NAME_KEY)
            .apply()
    }
}