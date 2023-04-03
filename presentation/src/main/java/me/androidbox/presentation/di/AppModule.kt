package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
<<<<<<< HEAD
import me.androidbox.presentation.util.CreatePhotoMultipart
import me.androidbox.presentation.util.imp.CreatePhotoMultipartImp
=======
import me.androidbox.data.alarm_manager.AlarmSchedulerImp
import me.androidbox.domain.alarm_manager.AlarmScheduler
import javax.inject.Singleton
>>>>>>> 1b530b34dc2e58da2cd177ec4f4f8d55e167a9aa

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

<<<<<<< HEAD
    @Binds
    fun bindsCreatePhotoMultipartImp(createPhotoMultipartImp: CreatePhotoMultipartImp): CreatePhotoMultipart
=======
    @Singleton
    @Binds
    fun binds(alarmSchedulerImp: AlarmSchedulerImp): AlarmScheduler
>>>>>>> 1b530b34dc2e58da2cd177ec4f4f8d55e167a9aa
}