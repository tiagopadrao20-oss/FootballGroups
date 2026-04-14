package com.gohan.footballgroups.di

import com.gohan.footballgroups.data.local.dao.EventDao
import com.gohan.footballgroups.data.local.dao.GroupDao
import com.gohan.footballgroups.data.repository.EventRepositoryImpl
import com.gohan.footballgroups.data.repository.GroupRepositoryImpl
import com.gohan.footballgroups.domain.repository.EventRepository
import com.gohan.footballgroups.domain.repository.GroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds domain [Repository] interfaces to their data-layer
 * implementations, keeping the domain module free of Android/Hilt dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGroupRepository(groupDao: GroupDao): GroupRepository =
        GroupRepositoryImpl(groupDao)

    @Provides
    @Singleton
    fun provideEventRepository(eventDao: EventDao): EventRepository =
        EventRepositoryImpl(eventDao)
}
