package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.androidbox.presentation.util.CreatePhotoMultipart
import me.androidbox.presentation.util.imp.CreatePhotoMultipartImp

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Binds
    fun bindsCreatePhotoMultipartImp(createPhotoMultipartImp: CreatePhotoMultipartImp): CreatePhotoMultipart
}