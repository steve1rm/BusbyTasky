package me.androidbox.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.androidbox.data.local.converter.EventConverter
import me.androidbox.data.remote.preference.PreferenceRepositoryImp
import me.androidbox.domain.authentication.preference.PreferenceRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesPreferenceRepositoryImp(preferenceRepositoryImp: PreferenceRepositoryImp): PreferenceRepository

    companion object {
        private const val SECRET_SHARED_PREFERENCES = "secret_shared_preferences"

        @Provides
        fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            val masterKey = MasterKey
                .Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                SECRET_SHARED_PREFERENCES,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        @Provides
        fun providesEventConverter(): EventConverter {
            return EventConverter()
        }
    }
}