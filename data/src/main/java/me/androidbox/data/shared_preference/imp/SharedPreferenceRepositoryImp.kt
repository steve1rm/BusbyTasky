package me.androidbox.data.shared_preference.imp


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import me.androidbox.domain.authentication.token_repository.TokenRepository
import javax.inject.Inject

class SharedPreferenceRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenRepository {

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

    override suspend fun saveUserToken(key: String, token: String) {
        sharedPreferences
            .edit()
            .putString(key, token).apply()
    }

    override suspend fun retrieveUserToken(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}