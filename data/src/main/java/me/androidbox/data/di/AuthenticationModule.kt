package me.androidbox.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.androidbox.data.remote.authentication.AuthenticationImp
import me.androidbox.domain.authentication.remote.Authentication

@InstallIn(ViewModelComponent::class)
@Module
interface AuthenticationModule {

    @Binds
    fun bindsAuthenticationImp(authenticationImp: AuthenticationImp): Authentication
}