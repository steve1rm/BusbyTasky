package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.domain.login.CredentialValidator
import me.androidbox.presentation.login.CredentialValidatorImp

@Module
@InstallIn(ViewModelComponent::class)
interface AuthenticateModule {
    @Reusable
    @Binds
    fun bindsCredentialValidatorImp(credentialValidatorImp: CredentialValidatorImp): CredentialValidator
}