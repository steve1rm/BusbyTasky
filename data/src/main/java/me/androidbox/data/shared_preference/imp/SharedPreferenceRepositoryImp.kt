package me.androidbox.data.shared_preference.imp


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import me.androidbox.domain.authentication.token_repository.PreferenceRepository
import javax.inject.Inject

class SharedPreferenceRepositoryImp @Inject constructor(
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

    override suspend fun saveToken(token: String) {
        sharedPreferences
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    override suspend fun saveUserId(userId: String) {
        sharedPreferences
            .edit()
            .putString(USER_ID_KEY, userId)
            .apply()
    }

    override suspend fun saveFullName(fullName: String) {
        sharedPreferences
            .edit()
            .putString(FULL_NAME_KEY, fullName)
            .apply()
    }

    override suspend fun retrieveCurrentUser() {

    }
}