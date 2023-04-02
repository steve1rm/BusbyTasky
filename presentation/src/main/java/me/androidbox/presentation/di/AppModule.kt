package me.androidbox.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.androidbox.data.alarm_manager.AlarmSchedulerImp
import me.androidbox.domain.alarm_manager.AlarmScheduler
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Singleton
    @Binds
    fun binds(alarmSchedulerImp: AlarmSchedulerImp): AlarmScheduler
}