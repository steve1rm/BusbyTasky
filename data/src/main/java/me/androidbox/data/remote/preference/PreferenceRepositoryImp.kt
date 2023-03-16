package me.androidbox.data.remote.preference


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import me.androidbox.domain.authentication.model.LoginUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import javax.inject.Inject

class PreferenceRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceRepository {

    companion object {
        const val TOKEN_KEY = "token_key"
        const val USER_ID_KEY = "user_id_key"
        const val FULL_NAME_KEY = "full_name"
    }

    private val masterKey by lazy {
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "secret_shared_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveToken(token: String) {
        sharedPreferences
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override fun saveUserId(userId: String) {
        sharedPreferences
            .edit()
            .putString(USER_ID_KEY, userId)
            .apply()
    }

    override fun saveFullName(fullName: String) {
        sharedPreferences
            .edit()
            .putString(FULL_NAME_KEY, fullName)
            .apply()
    }

    override fun retrieveCurrentUserOrNull(): LoginUser? {
        val token = sharedPreferences.getString(TOKEN_KEY, "")
        val userId = sharedPreferences.getString(USER_ID_KEY, "")
        val fullName = sharedPreferences.getString(FULL_NAME_KEY, "")

        return if(!token.isNullOrEmpty() && !userId.isNullOrEmpty() && !fullName.isNullOrEmpty()) {
            LoginUser(
                token = token,
                userId = userId,
                fullName = fullName
            )
        }
        else {
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