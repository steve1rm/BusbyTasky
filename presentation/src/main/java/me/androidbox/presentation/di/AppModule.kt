package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import me.androidbox.data.alarm_manager.AlarmSchedulerImp
import me.androidbox.data.worker_manager.util.CreatePhotoMultipart
import me.androidbox.data.worker_manager.util.imp.CreatePhotoMultipartImp
import me.androidbox.domain.alarm_manager.AlarmScheduler

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Binds
    fun bindsCreatePhotoMultipartImp(createPhotoMultipartImp: CreatePhotoMultipartImp): CreatePhotoMultipart

    @Singleton
    @Binds
    fun binds(alarmSchedulerImp: AlarmSchedulerImp): AlarmScheduler
}