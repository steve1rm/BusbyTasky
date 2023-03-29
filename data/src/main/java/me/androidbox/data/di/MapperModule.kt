package me.androidbox.data.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.mapper.DataToDomainMapper
import me.androidbox.data.mapper.imp.DataToDomainMapperImp
import me.androidbox.domain.authentication.model.Event

@InstallIn(SingletonComponent::class)
@Module
interface MapperModule {

    @Reusable
    @Binds
    fun bindsDataToDomainMapperImp(dataToDomainMapperImp: DataToDomainMapperImp)
            : DataToDomainMapper<List<EventEntity>, List<Event>>

}